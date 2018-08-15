package blog.javamagic.pfp.antlr;

final class PFPSyntax {

	public final static String string( final String source ) {
		return source.substring( 1, source.length() - 1 );
	}

	public final static String filename( final String filename ) {
		return string( filename ).replaceAll( "\\\\", "/" );
	}

}
