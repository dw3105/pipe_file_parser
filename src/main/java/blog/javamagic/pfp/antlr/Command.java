package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.parser.PipeFileParser;

public final class Command extends AbstractContainer {

	private Parser fParser;
	private Output fOutput;
	private String fHeader;

	public final void setParser( final Parser parser ) {
		fParser = parser;
	}

	public final void setOutput( final Output output ) {
		fOutput = output;
	}

	@Override
	protected final void execute() {
		final PipeFileParser parser = fParser.pipeFileParser();
		if ( fHeader != null ) {
			parser.setHeader( fHeader );
		}
		if ( fOutput != null ) {
			if ( fOutput.usesStdout() ) {
				PFP.resolveStdoutLoggingConflict();
			}
			fOutput.output( parser );
		}
		else {
			PFP.resolveStdoutLoggingConflict();
			parser.output();
		}
	}

	public final void setHeader( final String header ) {
		fHeader = header;
	}

}
