package blog.javamagic.pfp.source;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.logger.Logger;

final class FileSource extends AbstractSource {

	private final String fFilename;

	public FileSource( final String filename ) {
		fFilename = filename;
	}

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Parsing file '%1$s'...",
				() -> new Object[] { fFilename }
		);
		final AtomicInteger lines_read = new AtomicInteger( 0 );
		try (
				final BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								new FileInputStream( fFilename ),
								"utf-8"
						)
				);
		) {
			while ( true ) {
				final String str = reader.readLine();
				if ( ( str == null ) || stopped() ) {
					break;
				}
				Logger.log(
						PFP.LOG_LEVEL_ALL,
						() -> "Line read:\t%1$s",
						() -> new Object[] { str }
				);
				lines_read.incrementAndGet();
				consumer.accept( new String[] { str } );
			}
		}
		catch ( Throwable e ) {
			Logger.log(
					PFP.LOG_LEVEL_ERROR,
					() -> "Exception (%1$s) caught while parsing file '%2$s'",
					() -> new Object[] { e.getMessage(), fFilename }
			);
		}
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Completed parsing file '%1$s', lines read: %2$d",
				() -> new Object[] { fFilename, lines_read.get() }
		);
	}

}
