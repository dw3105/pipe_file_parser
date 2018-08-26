package blog.javamagic.pfp.variable;

import java.util.HashMap;
import java.util.Map;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.logger.Logger;

public final class Variables {
	
	private static final class Variable {

		private final Integer intValue;
		private final String stringValue;

		public Variable( final int intValue ) {
			this.intValue = intValue;
			this.stringValue = null;
		}

		public Variable( final String stringValue ) {
			this.intValue = null;
			this.stringValue = stringValue;
		}

	}

	private final static Map<String, Variable> fVariables = new HashMap<>();

	public final static void set( final String name, final int intValue ) {
		if ( !fVariables.containsKey( name ) ) {
			VariablesRegistry.registerVariable( name );
		}
		fVariables.put( name, new Variable( intValue ) );
		Logger.log(
				PFP.LOG_LEVEL_DEBUG,
				() -> "%1$s = %2$d",
				() -> new Object[] { name, intValue }
		);
	}

	public final static void set( final String name, final String stringValue ) {
		if ( !fVariables.containsKey( name ) ) {
			VariablesRegistry.registerVariable( name );
		}
		fVariables.put( name, new Variable( stringValue ) );
		Logger.log(
				PFP.LOG_LEVEL_DEBUG,
				() -> "%1$s = %2$s",
				() -> new Object[] { name, stringValue }
		);
	}

	public final static void setColumn( final String name, final int column ) {
		if ( !fVariables.containsKey( name ) ) {
			VariablesRegistry.registerVariable( name );
		}
		final String str = PFP.currentLine()[column];
		fVariables.put( name, new Variable( str ) );
		Logger.log(
				PFP.LOG_LEVEL_DEBUG,
				() -> "%1$s = #%2$s",
				() -> new Object[] { name, str }
		);
	}

	public final static String getString( final String name ) {
		if ( !fVariables.containsKey( name ) ) {
			throw new RuntimeException(
					"Variable '" + name + "' is not registered"
			);
		}
		
		final Variable variable = fVariables.get( name );
		
		final String result;
		if ( variable.stringValue != null ) {
			result = variable.stringValue;
		}
		else {
			result = variable.intValue.toString();
		}
		return result;
	}

}
