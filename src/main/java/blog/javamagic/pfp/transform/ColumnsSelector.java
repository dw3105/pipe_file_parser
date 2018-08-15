package blog.javamagic.pfp.transform;

import java.util.Arrays;

final class ColumnsSelector implements LineTransform {

	private final int[] fColumns;
	private final int fLength;

	public ColumnsSelector( final int[] columns ) {
		fLength = columns.length;
		fColumns = Arrays.copyOf( columns, fLength );
	}

	@Override
	public final String[] t( final String[] line ) {
		final String[] new_line = new String[fLength];
		for ( int i = 0; i < fLength; ++i ) {
			new_line[i] = line[fColumns[i]];
		}
		return new_line;
	}

}
