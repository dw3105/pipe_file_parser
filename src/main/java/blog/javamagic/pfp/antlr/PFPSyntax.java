package blog.javamagic.pfp.antlr;

final class PFPSyntax {

	public final static String filename( final String filename ) {
		return filename.replaceAll( "\\\\", "/" );
	}

}
