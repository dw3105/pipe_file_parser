package blog.javamagic.pfp.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.file.WildcardMatcher;
import blog.javamagic.pfp.logger.Logger;
import blog.javamagic.pfp.parser.PipeFileParser;

final class FileConcatenator extends AbstractSource {

	private final String[] fFileMasks;

	public FileConcatenator( final String[] fileMasks ) {
		fFileMasks = Arrays.copyOf( fileMasks, fileMasks.length );
	}

	public FileConcatenator( final PipeFileParser parser ) {
		final List<String> masks = new ArrayList<>();
		parser.parse( ( line ) -> masks.add( line[0] ) );
		fFileMasks = masks.toArray( new String[masks.size()] );
	}

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		final List<String> files_list = new ArrayList<>();
		for ( final String mask : fFileMasks ) {
			WildcardMatcher.match( mask, files_list::add );
		}
		files_list.forEach(
				( file ) -> new FileSource( file ).forEachLine(
						( line ) -> {
							if ( stopped() ) {
								return;
							}
							consumer.accept( line );
						}
				)
		);
		if ( files_list.isEmpty() ) {
			Logger.log(
					PFP.LOG_LEVEL_ERROR,
					() -> "No files were found matching masks %1$s",
					() -> new Object[] { Arrays.toString( fFileMasks ) }
			);
		}
	}

}
