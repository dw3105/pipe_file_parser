package blog.javamagic.pfp.transform;

final class CharReplacer implements LineTransform {

	private final char fC;
	private final Character fR;

	public CharReplacer( final char c, final Character r ) {
		fC = c;
		fR = r;
	}

	@Override
	public final String[] t( final String[] line ) {
		final String[] new_line = new String[line.length];
		for ( int i = 0; i < line.length; ++i ) {
			new_line[i] = replace( fC, fR, line[i] );
		}
		return new_line;
	}

	private final static String replace(
			final char c,
			final Character r,
			final String str
	) {
		final StringBuilder sb = new StringBuilder();
		for ( final char src : str.toCharArray() ) {
			if ( src == c ) {
				if ( r != null ) {
					sb.append( r );
				}
			}
			else {
				sb.append( c );
			}
		}
		return sb.toString();
	}

}
