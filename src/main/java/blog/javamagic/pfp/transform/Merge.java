package blog.javamagic.pfp.transform;

final class Merge implements LineTransform {

	private final String fSeparator;

	public Merge( final String separator ) {
		fSeparator = separator;
	}

	@Override
	public final String[] t( final String[] line ) {
		final StringBuilder sb = new StringBuilder( line[0] );
		for ( int i = 1; i < line.length; ++i ) {
			sb.append( fSeparator ).append( line[i] );
		}
		return new String[] { sb.toString() };
	}

}
