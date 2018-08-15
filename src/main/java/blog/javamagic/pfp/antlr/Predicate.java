package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.filter.LineFilters;
import blog.javamagic.pfp.filter.ImportedFilter;

public final class Predicate extends AbstractContainer {

	public enum Type {
		contains,
		inDictionary,
		custom		
	}

	private Type fType;
	private String fString;
	private Parameters fParameters = new Parameters();
	private String fName;

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void setString( final String string ) {
		fString = PFPSyntax.string( string );
	}

	public final void setParameters( final Parameters parameters ) {
		fParameters = parameters;
	}

	public final void setName( final String name ) {
		fName = name;
	}

	public final java.util.function.Predicate<String[]> predicate() {
		final java.util.function.Predicate<String[]> predicate;
		switch ( fType ) {
		case contains:
			predicate = LineFilters.contains( fString )::f;
			break;
		case custom:
			final ImportedFilter filter = LineFilters.importedFilter( fName );
			if ( fParameters != null ) {
				filter.setParameters( fParameters );
			}
			predicate = filter::f;
			break;
		case inDictionary:
			int column = 0;
			int index_column = 0;
			Dictionary dict = null;
			int dict_column = 0;
			boolean ignore_case = false;
			switch ( fParameters.count() ) {
			case 2:
				column = fParameters.parameter( 0 ).integer();
				dict = Dictionaries.get( fParameters.parameter( 1 ).variable() );
				index_column = column;
				break;
			case 3:
				column = fParameters.parameter( 0 ).integer();
				dict = Dictionaries.get( fParameters.parameter( 1 ).variable() );
				index_column = column;
				ignore_case = fParameters.parameter( 2 ).bool();
				break;
			case 4:
				column = fParameters.parameter( 0 ).integer();
				dict = Dictionaries.get( fParameters.parameter( 1 ).variable() );
				ignore_case = fParameters.parameter( 2 ).bool();
				index_column = fParameters.parameter( 3 ).integer();
				break;
			case 5:
				column = fParameters.parameter( 0 ).integer();
				dict = Dictionaries.get( fParameters.parameter( 1 ).variable() );
				ignore_case = fParameters.parameter( 2 ).bool();
				index_column = fParameters.parameter( 3 ).integer();
				dict_column = fParameters.parameter( 4 ).integer();
				break;
			default:
				throw new RuntimeException(
						"Invalid parameters count - " + fParameters.count()
				);
			}
			predicate = LineFilters.inDictionary(
					column,
					index_column,
					dict,
					dict_column,
					ignore_case
			)::f;
			break;
		default:
			throw new Error( "Invalid type - " + fType );
		}
		return predicate;
	}

}
