package blog.javamagic.pfp.file;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public final class WildcardMatcher {

	public final static void match(
			final String mask,
			final Consumer<File> consumer
	) {
		final String slashed_mask = mask.trim().replaceAll( "\\\\", "/" );
		if ( !"".equals( mask ) ) {
			final boolean from_root = isFromRoot( slashed_mask );
			final String base = getBase( slashed_mask, from_root );
			final String relative_mask = relativeMask( slashed_mask, from_root );
			matchFiles( base, relative_mask, consumer );
		}
	}

	private final static void matchFiles(
			final String base,
			final String mask,
			final Consumer<File> consumer
	) {
		if ( mask == "" ) {
			final File base_file = new File( base );
			if ( base_file.exists() && base_file.isFile() ) {
				final File normalized = Paths.get( base )
						.toAbsolutePath()
						.normalize()
						.toFile();
				consumer.accept( normalized );
			}
		}
		else {
			final String[] parts = mask.split( "/" );
			String new_base = base;
			boolean wildcard_found = false;
			for ( int i = 0; i < parts.length; ++i ) {
				final String part = parts[i];
				if ( containsWildcard( part ) ) {
					wildcard_found = true;
					final File base_dir = new File( new_base );
					if ( base_dir.exists() && base_dir.isDirectory() ) {
						try (
								DirectoryStream<Path> dirStream =
										Files.newDirectoryStream(
												Paths.get( new_base ),
												part
										)
						) {
							String remaining_mask = "";
							for ( int j = i + 1; j < parts.length; ++j ) {
								if ( j > i + 1 ) {
									remaining_mask += "/";
								}
								remaining_mask += parts[j];
							}
							final String rem_mask = remaining_mask;
							dirStream.forEach(
									path -> matchFiles(
											path.toString(),
											rem_mask,
											consumer
									)
							);
						}
						catch ( Throwable e ) {
							throw new RuntimeException( e );
						} 
					}
					break;
				}
				else {
					if ( new_base.length() > 0 ) {
						new_base += "/";
					}
					new_base += part;
				}
			}
			if ( !wildcard_found ) {
				matchFiles( new_base, "", consumer );
			}
		}
	}

	private final static boolean containsWildcard( final String mask ) {
		return mask.contains( "?" ) || mask.contains( "*" );
	}

	private final static String relativeMask(
			final String mask,
			final boolean fromRoot
	) {
		if ( fromRoot ) {
			if ( isWindowsPath( mask ) ) {
				String rel_mask = "";
				final String[] parts = mask.split( "/" );
				for ( int i = 1; i < parts.length; ++i ) {
					if ( i > 1 ) {
						rel_mask += "/";
					}
					rel_mask += parts[i];
				}
				return rel_mask;
			}
			else {
				return mask.substring( 1 );
			}
		}
		else {
			return mask;
		}
	}

	private final static String getBase(
			final String mask,
			final boolean fromRoot
	) {
		if ( fromRoot ) {
			if ( isWindowsPath( mask ) ) {
				return mask.split( "/" )[0] + "/";
			}
			else {
				return "/";
			}
		}
		else {
			return Paths.get( "." )
					.toAbsolutePath()
					.normalize()
					.toString()
					.replaceAll( "\\\\", "/" );
		}
	}

	private final static boolean isFromRoot( final String mask ) {
		if ( "/".equals( mask.substring( 0, 1 ) ) ) {
			return true;
		}
		if ( isWindowsPath( mask ) ) {
			return true;
		}
		return false;
	}

	private final static boolean isWindowsPath( final String mask ) {
		return mask.substring( 0, 3 ).contains( ":" );
	}

}
