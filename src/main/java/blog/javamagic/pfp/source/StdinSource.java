package blog.javamagic.pfp.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.logger.Logger;

final class StdinSource extends AbstractSource {

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Reading from stdin...",
				() -> null
		);
		final AtomicInteger lines_read = new AtomicInteger( 0 );
		try (
				final BufferedReader reader =
						new BufferedReader(
								new InputStreamReader( System.in )
						)
		) {
			while ( true ) {
				final String line = reader.readLine();
				if ( ( line == null ) || stopped() ) {
					break;
				}
				Logger.log(
						PFP.LOG_LEVEL_ALL,
						() -> "Line read:\t%1$s",
						() -> new Object[] { line }
				);
				lines_read.incrementAndGet();
				consumer.accept( new String[] { line } );
			}
		}
		catch ( IOException e ) {
			Logger.log(
					PFP.LOG_LEVEL_ERROR,
					() -> "Exception (%1$s) caught while reading from stdin",
					() -> new Object[] { e.getMessage() }
			);
		}
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Completed reading from stdin, lines read: %1$d",
				() -> new Object[] { lines_read.get() }
		);
	}

}
