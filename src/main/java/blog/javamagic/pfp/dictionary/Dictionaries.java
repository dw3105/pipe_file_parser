package blog.javamagic.pfp.dictionary;

import java.util.HashMap;
import java.util.Map;

import blog.javamagic.pfp.parser.PipeFileParser;
import blog.javamagic.pfp.variable.VariablesRegistry;

public final class Dictionaries {
	
	private final static Map<String, Dictionary> fDictionaries = new HashMap<>();

	public final static Dictionary create(
			final PipeFileParser parser,
			final int idColumn
	) {
		return new DictionaryImpl( parser, idColumn );
	}

	public final static Dictionary get( final String variable ) {
		if ( !fDictionaries.containsKey( variable ) ) {
			throw new RuntimeException(
					"Dictionary '" + variable + "' is not registered"
			);
		}
		
		return fDictionaries.get( variable );
	}

	public final static void registerDictionary(
			final String name,
			final Dictionary dictionary
	) {
		if ( !VariablesRegistry.registered( name ) ) {
			VariablesRegistry.registerDictionary( name );
			fDictionaries.put( name, dictionary );
		}
		else {
			throw new RuntimeException(
					"Variable " + name + " is already registered"
			);
		}
	}

}
