package blog.javamagic.pfp.source;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import blog.javamagic.pfp.parser.PipeFileParser;

final class CountLinesSource extends AbstractSource {

	private final PipeFileParser fParser;

	public CountLinesSource( final PipeFileParser parser ) {
		fParser = parser;
	}

	@Override
	public void forEachLine( Consumer<String[]> consumer ) {
		AtomicInteger lines_read = new AtomicInteger( 0 );
		fParser.parse(
				( line ) -> {
					if ( !stopped() ) {
						lines_read.incrementAndGet();
					}
					else {
						fParser.stop();
						return;
					}
				}
		);
		consumer.accept( new String[] { String.valueOf( lines_read.get() ) } );
	}

}
