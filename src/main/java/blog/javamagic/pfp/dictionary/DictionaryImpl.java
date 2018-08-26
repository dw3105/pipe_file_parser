package blog.javamagic.pfp.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import blog.javamagic.pfp.parser.PipeFileParser;

final class DictionaryImpl implements Dictionary {
	
	private final Map<String, String[]> fLines;
	private final List<String[]> fLinesList;
	private boolean fStopped = false;

	public DictionaryImpl( final PipeFileParser parser, final int idColumn ) {
		fLines = new HashMap<>();
		fLinesList = new ArrayList<>();
		parser.parse(
				( line ) -> { 
					fLines.put( line[idColumn], line );
					fLinesList.add( line );
				}
		);
	}

	@Override
	public final String[] line( final String id ) {
		return fLines.get( id );
	}

	@Override
	public void forEachLine( Consumer<String[]> consumer ) {
		fStopped = false;
		for ( final String[] line : fLinesList ) {
			if ( fStopped ) {
				break;
			}
			consumer.accept( line );
		}
	}

	@Override
	public void stop() {
		fStopped = true;
	}

}
