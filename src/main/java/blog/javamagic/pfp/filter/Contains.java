package blog.javamagic.pfp.filter;

final class Contains implements LineFilter {

	private final String fSubstring;

	public Contains( final String substring ) {
		fSubstring = substring;
	}

	@Override
	public final boolean f( final String[] line ) {
		for ( final String part : line ) {
			if ( part.contains( fSubstring ) ) {
				return true;
			}
		}
		return false;
	}

}
