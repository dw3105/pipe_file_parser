package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.PFP;

public final class Program extends AbstractContainer {

	@Override
	public final void execute() {
		forEachChild( ( child ) -> child.execute() );
		
		PFP.closeLog();
	}

}
