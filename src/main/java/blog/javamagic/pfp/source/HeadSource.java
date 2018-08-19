package blog.javamagic.pfp.source;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import blog.javamagic.pfp.parser.PipeFileParser;

final class HeadSource extends AbstractSource {

	private final PipeFileParser fParser;
	private final int fLinesCount;

	public HeadSource( final PipeFileParser parser, final int linesCount ) {
		fParser = parser;
		fLinesCount = linesCount;
	}

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		AtomicInteger lines_read = new AtomicInteger( 0 );
		fParser.parse(
				( line ) -> {
					if ( ( lines_read.get() < fLinesCount ) && ( !stopped() ) ) {
						consumer.accept( line );
						lines_read.incrementAndGet();
					}
					else {
						fParser.stop();
						return;
					}
				}
		);
	}

}
