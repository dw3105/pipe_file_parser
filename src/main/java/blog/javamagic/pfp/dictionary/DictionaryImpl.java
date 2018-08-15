package blog.javamagic.pfp.dictionary;

import java.util.HashMap;
import java.util.Map;

import blog.javamagic.pfp.parser.PipeFileParser;

final class DictionaryImpl implements Dictionary {
	
	private final Map<String, String[]> fLines;

	public DictionaryImpl( final PipeFileParser parser, final int idColumn ) {
		fLines = new HashMap<>();
		parser.parse( ( line ) -> fLines.put( line[idColumn], line ) );
	}

	@Override
	public final String[] line( final String id ) {
		return fLines.get( id );
	}

}
