package blog.javamagic.pfp.transform;

import java.util.ArrayList;
import java.util.List;

final class Split implements LineTransform {

	private final String fSeparator;

	public Split( final String separator ) {
		fSeparator = separator;
	}

	@Override
	public final String[] t( final String[] line ) {
		final List<String> list = new ArrayList<>();
		for ( String column : line ) {
			final String[] parts = column.split( fSeparator, -1 );
			for ( String part : parts ) {
				list.add( part );
			}
		}
		return list.toArray( new String[0] );
	}

}
