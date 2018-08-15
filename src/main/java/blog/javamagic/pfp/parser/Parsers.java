package blog.javamagic.pfp.parser;

import blog.javamagic.pfp.source.Source;

public final class Parsers {

	public final static PipeFileParser create( final Source source ) {
		return new PipeFileParserImpl( source );
	}

}
