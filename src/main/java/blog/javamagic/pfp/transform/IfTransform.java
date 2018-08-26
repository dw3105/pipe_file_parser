package blog.javamagic.pfp.transform;

import java.util.function.Predicate;

import blog.javamagic.pfp.branch.Branch;

final class IfTransform implements LineTransform {

	private final Predicate<String[]> fPredicate;
	private final Branch fMainBranch;
	private final Branch fAlternativeBranch;

	public IfTransform(
			final Predicate<String[]> predicate,
			final Branch mainBranch,
			final Branch alternativeBranch
	) {
		fPredicate = predicate;
		fMainBranch = mainBranch;
		fAlternativeBranch = alternativeBranch;
	}

	@Override
	public final String[] t( final String[] line ) {
		if ( fPredicate.test( line ) ) {
			return fMainBranch.processLine( line );
		}
		else if ( fAlternativeBranch != null ) {
			return fAlternativeBranch.processLine( line );
		}
		else {
			return line;
		}
	}

}
