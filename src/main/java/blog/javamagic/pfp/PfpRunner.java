package blog.javamagic.pfp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
			final String program_text = getProgramText( args );
			final CodePointCharStream char_stream =
					CharStreams.fromString( program_text );
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

	private final static String getProgramText( String[] args ) {
		if ( args.length == 0 ) {
			reportInvalidParameters();
			throw new RuntimeException( "Invalid command line parameters." );
		}
		final String param0 = args[0];
		final String program_text;
		if ( "-f".equals( param0 ) ) {
			if ( args.length < 2 ) {
				reportInvalidParameters();
				throw new RuntimeException( "Invalid command line parameters." );
			}
			final String program_file = args[1];
			program_text = readProgramFromFile( program_file );
		}
		else {
			program_text = param0;
		}
		return program_text;
	}

	private final static String readProgramFromFile( String programFile ) {
		try (
				final BufferedReader reader =
						new BufferedReader(
								new FileReader( new File( programFile ) )
						)
		) {
			final StringBuilder sb = new StringBuilder();
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				sb.append( line ).append( " " );
			}
			return sb.toString();
		}
		catch ( Throwable e ) {
			throw new RuntimeException(
					"Exception caught while reading program file '"
							+ programFile
							+ "': "
							+ e.getMessage(),
					e
			);
		}
	}

	private final static void reportInvalidParameters() {
		Logger.log( PFP.LOG_LEVEL_ERROR, () -> "Expected parameters:", () -> null );
		Logger.log( PFP.LOG_LEVEL_ERROR, () -> "\"program_text\"", () -> null );
		Logger.log( PFP.LOG_LEVEL_ERROR, () -> "or", () -> null );
		Logger.log( PFP.LOG_LEVEL_ERROR, () -> "-f program_file", () -> null );
	}

}