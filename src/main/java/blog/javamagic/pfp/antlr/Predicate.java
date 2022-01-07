package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.filter.ImportedFilter;
import blog.javamagic.pfp.filter.LineFilters;

public final class Predicate extends AbstractContainer {

	public enum Type {
		contains,
		inDictionary,
		custom,
		string_comparison,
		lookup,
		beginsWith,
		endsWith,
		empty		
	}
	
	public enum Comparison {
		equals,
		not_equals
	}

	private Type fType;
	private Parameters fParameters = new Parameters();
	private String fName;
	private int fColumn = -1;
	private String fLeftString;
	private String fLeftVariable;
	private String fRightString;
	private String fRightVariable;
	private Comparison fComparison;
	private String fDictionary;
	
	private final List<String> fStringParameters =
			new ArrayList<String>();
	private final List<String> fVariableParameters =
			new ArrayList<String>();

	public final void setType( final Type type ) {
		fType = type;
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
		case empty:
			predicate = LineFilters.empty()::f;
			break;
		case string_comparison:
			switch ( fComparison ) {
			case equals:
				predicate = LineFilters.stringEqual(
						fLeftString,
						fLeftVariable,
						fRightString,
						fRightVariable
				)::f;
				break;
			case not_equals:
				predicate = LineFilters.stringNotEqual(
						fLeftString,
						fLeftVariable,
						fRightString,
						fRightVariable
				)::f;
				break;
			default:
				throw new Error( "Invalid comparison - " + fComparison );
			}
			break;
		case contains:
			predicate =
					LineFilters.contains(
							fStringParameters,
							fVariableParameters,
							fColumn
					)::f;
			break;
		case beginsWith:
			predicate =
					LineFilters.beginsWith(
							fStringParameters,
							fVariableParameters,
							fColumn
					)::f;
			break;
		case endsWith:
			predicate =
					LineFilters.endsWith(
							fStringParameters,
							fVariableParameters,
							fColumn
					)::f;
			break;
		case lookup:
			predicate = LineFilters.lookup( fDictionary, fColumn )::f;
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

	public final void setColumn( final int column ) {
		fColumn = column;
	}

	public final void setLeftString( final String str ) {
		fLeftString = str;
	}

	public final void setLeftVariable( final String name ) {
		fLeftVariable = name;
	}

	public final void setRightString( final String str ) {
		fRightString = str;
	}

	public final void setRightVariable( final String name ) {
		fRightVariable = name;
	}

	public final void setComparison( final Comparison comp ) {
		fComparison = comp;
	}

	public final void setDictionary( final String dictionary ) {
		fDictionary = dictionary;
	}

	public final void addStringParam( final String str ) {
		fStringParameters.add( str );
	}

	public final void addVariableParam( final String variable ) {
		fVariableParameters.add( variable );
	}

}
