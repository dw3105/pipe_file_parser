package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.parser.Parsers;
import blog.javamagic.pfp.parser.PipeFileParser;
import blog.javamagic.pfp.source.Source;

public final class Parser extends AbstractContainer {

	private Input fInput;
	private boolean fUnique = false;

	public final void setInput( final Input input ) {
		fInput = input;
	}

	public final void unique() {
		fUnique = true;
	}

	public final PipeFileParser pipeFileParser() {
		final Source source = fInput.source();
		final PipeFileParser parser = Parsers.create( source );
		forEachChild(
				( child ) -> {
					if ( child instanceof Filter ) {
						final Filter filter = (Filter) child;
						filter.appentTo( parser );
					}
					else if ( child instanceof Transform ) {
						final Transform transform = (Transform) child;
						transform.appendTo( parser );
					}
				}
		);
		if ( fUnique ) {
			parser.unique();
		}
		return parser;
	}

}
