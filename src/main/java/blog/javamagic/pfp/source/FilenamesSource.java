package blog.javamagic.pfp.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.file.WildcardMatcher;
import blog.javamagic.pfp.logger.Logger;

final class FilenamesSource extends AbstractSource {

	private final String[] fFileMasks;

	public FilenamesSource( final String[] fileMasks ) {
		fFileMasks = Arrays.copyOf( fileMasks, fileMasks.length );
	}

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		final List<String> files_list = new ArrayList<>();
		for ( final String mask : fFileMasks ) {
			WildcardMatcher.match( mask, files_list::add );
		}
		files_list.forEach(
				( file ) -> {
					if ( stopped() ) {
						return;
					}
					consumer.accept( new String[] { file } );
				}
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
