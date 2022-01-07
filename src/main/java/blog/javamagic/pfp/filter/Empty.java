package blog.javamagic.pfp.filter;

final class Empty implements LineFilter {

	@Override
	public final boolean f( final String[] line ) {
		for ( final String column : line ) {
			if ( !"".equals( column ) ) {
				return false;
			}
		}
		return true;
	}

}
