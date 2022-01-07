package blog.javamagic.pfp.source;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
		final List<File> files_list = new ArrayList<>();
		for ( final String mask : fFileMasks ) {
			WildcardMatcher.match( mask, files_list::add );
		}
		final ExecutorService executor = Executors.newFixedThreadPool( 32 );
		files_list.forEach(
				( file ) -> {
					if ( stopped() ) {
						return;
					}
					final Runnable worker = new Runnable() {
						
						@Override
						public void run() {
							final String path = file.getAbsolutePath();
							final String size = String.valueOf( file.length() );
							final String date =
									new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss.SSS" )
											.format( new Date( file.lastModified() ) );
							final String[] line = new String[] { path, size, date };
							synchronized ( FilenamesSource.class ) {
								consumer.accept( line );
							}
						}
					};
					executor.execute( worker );
				}
		);
		executor.shutdown();
		while ( !executor.isTerminated() ) {
			try {
				Thread.sleep( 10 );
			}
			catch ( InterruptedException e ) {
				Thread.currentThread().interrupt();
			}
		}
		if ( files_list.isEmpty() ) {
			Logger.log(
					PFP.LOG_LEVEL_ERROR,
					() -> "No files were found matching masks %1$s",
					() -> new Object[] { Arrays.toString( fFileMasks ) }
			);
		}
	}

}
