package blog.javamagic.pfp.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.variable.VariablesRegistry;

public final class LineFilters {
	
	private final static Map<String, ImportedFilter> fRegisteredFilters =
			new HashMap<>();

	public final static LineFilter contains(
			final String substring,
			final String variable,
			final int column
	) {
		return new Contains( substring, variable, column );
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

	public final static LineFilter stringEqual(
			final String leftString,
			final String leftVariable,
			final String rightString,
			final String rightVariable
	) {
		return new StringEqual(
				leftString,
				leftVariable,
				rightString,
				rightVariable
		);
	}

	public final static LineFilter stringNotEqual(
			final String leftString,
			final String leftVariable,
			final String rightString,
			final String rightVariable
	) {
		return new StringNotEqual(
				leftString,
				leftVariable,
				rightString,
				rightVariable
		);
	}

	public final static LineFilter lookup(
			final String dictionary,
			final int column
	) {
		return new Lookup( dictionary, column );
	}

	public final static LineFilter contains(
			final List<String> stringParameters,
			final List<String> variableParameters,
			final int column
	) {
		return new Contains( stringParameters, variableParameters, column );
	}

	public final static LineFilter beginsWith(
			final List<String> stringParameters,
			final List<String> variableParameters,
			final int column
	) {
		return new BeginsWith( stringParameters, variableParameters, column );
	}

	public final static LineFilter endsWith(
			final List<String> stringParameters,
			final List<String> variableParameters,
			final int column
	) {
		return new EndsWith( stringParameters, variableParameters, column );
	}

	public final static LineFilter empty() {
		return new Empty();
	}

}
