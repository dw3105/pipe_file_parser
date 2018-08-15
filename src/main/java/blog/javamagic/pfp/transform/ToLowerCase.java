package blog.javamagic.pfp.transform;

import java.util.Arrays;

final class ToLowerCase implements LineTransform {

	private final int fTargetColumn;

	public ToLowerCase( final int targetColumn ) {
		fTargetColumn = targetColumn;
	}

	@Override
	public final String[] t( final String[] line ) {
		final String[] new_line = Arrays.copyOf( line, line.length );
		new_line[fTargetColumn] = line[fTargetColumn].toLowerCase();
		return new_line;
	}

}
