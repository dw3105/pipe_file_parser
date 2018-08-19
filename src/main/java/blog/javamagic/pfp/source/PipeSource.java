package blog.javamagic.pfp.source;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.logger.Logger;
import blog.javamagic.pfp.parser.PipeFileParser;

final class PipeSource extends AbstractSource {

	private final PipeFileParser fParser;

	public PipeSource( final PipeFileParser parser ) {
		fParser = parser;
	}

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Receiving lines from pipe...",
				() -> null
		);
		final AtomicInteger lines_received = new AtomicInteger( 0 );
		try {
			fParser.parse( ( line ) -> {
				if ( stopped() ) {
					fParser.stop();
					return;
				}
				Logger
						.log(
								PFP.LOG_LEVEL_ALL,
								() -> "Line received from pipe:\t%1$s",
								() -> new Object[] { Arrays.toString( line ) }
						);
				lines_received.incrementAndGet();
				consumer.accept( line );
			} );
		}
		finally {
			Logger.log(
					PFP.LOG_LEVEL_INFO,
					() -> "Completed receiving lines from pipe, lines received: %1$d",
					() -> new Object[] { lines_received.get() }
			);
		}
	}

}
