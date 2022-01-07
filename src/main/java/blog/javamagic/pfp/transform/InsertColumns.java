package blog.javamagic.pfp.transform;

import java.util.Arrays;

final class InsertColumns implements LineTransform {

	private final int fPosition;
	private final int fCount;

	public InsertColumns( final int position, final int count ) {
		if ( position < 0 ) {
			throw new RuntimeException(
					"position ("
							+ position
							+ ") must be greater or equal to 0"
			);
		}
		if ( count < 1 ) {
			throw new RuntimeException(
					"count ("
							+ count
							+ ") must be greater or equal to 1"
			);
		}
		fPosition = position;
		fCount = count;
	}

	@Override
	public final String[] t( final String[] line ) {
		final int len = line.length;
		final String[] new_line = new String[len + fCount];
		Arrays.fill( new_line, "" );
		if ( fPosition == 0 ) {
			for ( int i = 0; i < len; ++i ) {
				new_line[i + fCount] = line[i];
			}
		}
		else if ( fPosition < len ) {
			for ( int i = 0; i < fPosition; ++i ) {
				new_line[i] = line[i];
			}
			for ( int i = fPosition; i < len; ++i ) {
				new_line[i + fCount] = line[i];
			}
		}
		else if ( fPosition == len ) {
			for ( int i = 0; i < len; ++i ) {
				new_line[i] = line[i];
			}
		}
		else {
			throw new RuntimeException(
					"position ("
							+ fPosition
							+ ") must be less or equal to columns count ("
							+ len
							+
							")"
			);
		}
		return new_line;
	}

}
