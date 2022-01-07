package blog.javamagic.pfp.source;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.opencsv.CSVReader;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.logger.Logger;

final class CsvSource extends AbstractSource {

	private final String fFilename;

	public CsvSource( final String filename ) {
		fFilename = filename;
	}

	@SuppressWarnings( "deprecation" )
	@Override
	public void forEachLine( Consumer<String[]> consumer ) {
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Parsing CSV file '%1$s'...",
				() -> new Object[] { fFilename }
		);
		final AtomicInteger lines_read = new AtomicInteger( 0 );
		try (
				final CSVReader reader =
						new CSVReader(
								new BufferedReader(
										new InputStreamReader(
												new FileInputStream( fFilename ),
												"utf-8"
										)
								),
								',',
								'"'
						);
		) {
			String[] line;
			while ( ( line = reader.readNext() ) != null ) {
				if ( stopped() ) {
					break;
				}
				final String[] cur_line = line;
				Logger.log(
						PFP.LOG_LEVEL_ALL,
						() -> "Line read:\t%1$s",
						() -> new Object[] { Arrays.toString( cur_line ) }
				);
				lines_read.incrementAndGet();
				consumer.accept( line );
			}
		}
		catch ( Throwable e ) {
			Logger.log(
					PFP.LOG_LEVEL_ERROR,
					() -> "Exception (%1$s) caught while parsing CSV file '%2$s'",
					() -> new Object[] { e.getMessage(), fFilename }
			);
		}
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Completed parsing CSV file '%1$s', lines read: %2$d",
				() -> new Object[] { fFilename, lines_read.get() }
		);
	}

}
