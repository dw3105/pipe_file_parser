package blog.javamagic.pfp.filter;

import java.util.List;

import blog.javamagic.pfp.variable.Variables;

final class EndsWith implements LineFilter {

	private final List<String> fStringParameters;
	private final List<String> fVariableParameters;
	private final int fColumn;

	public EndsWith(
			final List<String> stringParameters,
			final List<String> variableParameters,
			final int column
	) {
		fStringParameters = stringParameters;
		fVariableParameters = variableParameters;
		fColumn = column;
	}

	@Override
	public boolean f( String[] line ) {
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
				if ( part.endsWith( substring ) ) {
					return true;
				}
			}
			return false;
		}
		else {
			return line[fColumn].endsWith( substring );
		}
	}

}
