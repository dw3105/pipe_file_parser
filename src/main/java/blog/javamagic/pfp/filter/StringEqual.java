package blog.javamagic.pfp.filter;

import blog.javamagic.pfp.variable.Variables;

final class StringEqual implements LineFilter {

	private final String fLeftString;
	private final String fLeftVariable;
	private final String fRightString;
	private final String fRightVariable;

	public StringEqual(
			final String leftString,
			final String leftVariable,
			final String rightString,
			final String rightVariable
	) {
		fLeftString = leftString;
		fLeftVariable = leftVariable;
		fRightString = rightString;
		fRightVariable = rightVariable;
	}

	@Override
	public final boolean f( final String[] line ) {
		final String left =
				fLeftString != null
				? fLeftString
						: Variables.getString( fLeftVariable );
		final String right =
				fRightString != null
				? fRightString
						: Variables.getString( fRightVariable );
		return left.equals( right );
	}

}
