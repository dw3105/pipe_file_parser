package blog.javamagic.pfp.filter;

import java.util.HashMap;
import java.util.Map;

import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.variable.VariablesRegistry;

public final class LineFilters {
	
	private final static Map<String, ImportedFilter> fRegisteredFilters =
			new HashMap<>();

	public final static LineFilter contains( final String substring ) {
		return new Contains( substring );
	}

	public final static LineFilter inDictionary(
			final int column,
			final int indexColumn,
			final Dictionary dict,
			final int dictColumn,
			final boolean ignoreCase
	) {
		return new InDictionary(
				column,
				indexColumn,
				dict,
				dictColumn,
				ignoreCase
		);
	}

	public final static void registerImportedFilter(
			final String name,
			final ImportedFilter filter
	) {
		if ( !VariablesRegistry.registered( name ) ) {
			VariablesRegistry.registerFilter( name );
			fRegisteredFilters.put( name, filter );
		}
		else {
			throw new RuntimeException(
					"Variable " + name + " is already registered"
			);
		}
	}

	public final static ImportedFilter importedFilter( final String name ) {
		if ( fRegisteredFilters.containsKey( name ) ) {
			return fRegisteredFilters.get( name );
		}
		else {
			throw new RuntimeException(
					"Filter " + name + " is not registered"
			);
		}
	}

}
