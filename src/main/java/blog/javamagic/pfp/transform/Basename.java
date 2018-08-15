package blog.javamagic.pfp.transform;

import java.io.File;
import java.util.Arrays;

final class Basename implements LineTransform {

	private final int fColumn;

	public Basename( final int column ) {
		fColumn = column;
	}

	@Override
	public final String[] t( final String[] line ) {
		final String[] new_line = Arrays.copyOf( line, line.length );
		new_line[fColumn] = new File( line[fColumn] ).getName();
		return new_line;
	}

}
