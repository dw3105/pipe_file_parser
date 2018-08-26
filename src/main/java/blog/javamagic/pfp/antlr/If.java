package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.branch.Branch;

public final class If extends AbstractContainer {

	private Predicate fPredicate;
	private IfBranch fMainBranch;
	private IfBranch fAlternativeBranch;

	public final void setPredicate( final Predicate predicate ) {
		fPredicate = predicate;
	}

	public final void setMainBranch( final IfBranch branch ) {
		fMainBranch = branch;
	}

	public final void setAlternativeBranch( final IfBranch branch ) {
		fAlternativeBranch = branch;
	}

	public final java.util.function.Predicate<String[]> predicate() {
		return fPredicate.predicate();
	}

	public Branch mainBranch() {
		return fMainBranch.branch();
	}

	public Branch alternativeBranch() {
		if ( fAlternativeBranch != null ) {
			return fAlternativeBranch.branch();
		}
		else {
			return null;
		}
	}

}
