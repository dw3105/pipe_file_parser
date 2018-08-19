package blog.javamagic.pfp.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import blog.javamagic.pfp.file.WildcardMatcher;

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
	}

}
