package blog.javamagic.pfp.source;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import blog.javamagic.pfp.parser.PipeFileParser;

final class TailSource extends AbstractSource {

	private final PipeFileParser fParser;
	private final int fLinesCount;

	public TailSource( final PipeFileParser parser, final int linesCount ) {
		fParser = parser;
		fLinesCount = linesCount;
	}

	@Override
	public void forEachLine( Consumer<String[]> consumer ) {
		final List<String[]> buffer = new LinkedList<>();
		fParser.parse(
				( line ) -> {
					buffer.add( line );
					while ( buffer.size() > fLinesCount ) {
						buffer.remove( 0 );
					}
				}
		);
		buffer.forEach(
				( line ) -> {
					if ( stopped() ) {
						return;
					}
					consumer.accept( line );
				}
		);
	}

}
