package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.branch.Branch;
import blog.javamagic.pfp.branch.Branches;

public final class IfBranch extends AbstractContainer {

	public Branch branch() {
		final Branch branch = Branches.create();
		forEachChild(
				( child ) -> {
					if ( child instanceof VarDefinitions ) {
						branch.append( ( (VarDefinitions) child ) );
					}
					else if ( child instanceof Transform ) {
						branch.append( (Transform) child );
					}
				}
		);
		return branch;
	}

}
