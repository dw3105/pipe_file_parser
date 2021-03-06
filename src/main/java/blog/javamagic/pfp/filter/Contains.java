package blog.javamagic.pfp.filter;

import java.util.ArrayList;
import java.util.List;

import blog.javamagic.pfp.variable.Variables;

final class Contains implements LineFilter {

	private final List<String> fStringParameters;
	private final List<String> fVariableParameters;
	private final int fColumn;

	public Contains(
			final String substring,
			final String variable,
			final int column
	) {
		fStringParameters = new ArrayList<>();
		if ( substring != null ) {
			fStringParameters.add( substring );
		}
		fVariableParameters = new ArrayList<>();
		if ( variable != null ) {
			fVariableParameters.add( variable );
		}
		fColumn = column;
	}

	public Contains(
			final List<String> stringParameters,
			final List<String> variableParameters,
			final int column
	) {
		fStringParameters = stringParameters;
		fVariableParameters = variableParameters;
		fColumn = column;
	}

	@Override
	public final boolean f( final String[] line ) {
		for ( final String str : fStringParameters ) {
			if ( testSubstring( line, str ) ) {
				return true;
			}
		}
		
		for ( final String variable : fVariableParameters ) {
			final String str = Variables.getString( variable );
			if ( testSubstring( line, str ) ) {
				return true;
			}
		}
		
		return false;
	}

	private final boolean testSubstring(
			final String[] line,
			final String substring
	) {
		if ( fColumn == -1 ) {
			for ( final String part : line ) {
				if ( part.contains( substring ) ) {
					return true;
				}
			}
			return false;
		}
		else {
			return line[fColumn].contains( substring );
		}
	}

}
