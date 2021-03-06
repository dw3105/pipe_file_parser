package blog.javamagic.pfp;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import blog.javamagic.pfp.antlr.PFPListener;
import blog.javamagic.pfp.antlr.Program;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxLexer;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser;
import blog.javamagic.pfp.logger.Logger;

public final class PfpRunner {

	public static void main( String[] args ) {
		try {
			final String rule = args[0];
			final CodePointCharStream char_stream =
					CharStreams.fromString( rule );
			final PFPSyntaxLexer lexer = new PFPSyntaxLexer( char_stream );
			final CommonTokenStream tokens = new CommonTokenStream( lexer );
			final PFPSyntaxParser parser = new PFPSyntaxParser( tokens );
			final ParseTree tree = parser.pfp_program();
			final ParseTreeWalker walker = new ParseTreeWalker();
			final PFPListener listener = new PFPListener();
			walker.walk( listener, tree );
			final Program program = listener.program();
			program.execute();
		}
		catch ( Throwable e ) {
			Logger.log(
					PFP.LOG_LEVEL_ERROR,
					() -> "Exception caught while running pfp program: %1$s%. "
							+ "Stack trace redirected to stderr",
					() -> new Object[] { e.getMessage() }
			);
			e.printStackTrace();
		}
		
		if ( Logger.errorCaught() ) {
			System.exit( -1 );
		}
		else {
			System.exit( 0 );
		}
	}

}
