package blog.javamagic.pfp.variable;

import java.util.HashMap;
import java.util.Map;

public final class VariablesRegistry {
	
	public enum VariableType {
		dictionary,
		filter,
		transform
	}
	
	private final static Map<String, VariableType> fRegisteredVariables =
			new HashMap<>();

	public final static boolean registered( final String name ) {
		return fRegisteredVariables.containsKey( name );
	}

	public final static void registerFilter( final String name ) {
		fRegisteredVariables.put( name, VariableType.filter );
	}

	public final static void registerTransform( final String name ) {
		fRegisteredVariables.put( name, VariableType.transform );
	}

	public final static void registerDictionary( final String name ) {
		fRegisteredVariables.put( name, VariableType.dictionary );
	}

}
