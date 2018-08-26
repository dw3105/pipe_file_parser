package blog.javamagic.pfp.branch;

import blog.javamagic.pfp.antlr.Transform;
import blog.javamagic.pfp.antlr.VarDefinitions;

public interface Branch {

	String[] processLine( String[] line );

	Branch append( VarDefinitions varDefinitions );
	Branch append( Transform transform );

}
