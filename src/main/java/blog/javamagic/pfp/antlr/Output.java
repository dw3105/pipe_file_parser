package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.parser.PipeFileParser;

public final class Output extends AbstractContainer {

	public enum Type {
		file,
		stdout,
		countLines		
	}

	private Type fType;
	private String fFilename;
	private String fSeparator = "";

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void setFilename( final String filename ) {
		fFilename = PFPSyntax.filename( filename );
	}

	public final void setSeparator( final String separator ) {
		fSeparator = PFPSyntax.string( separator );
	}

	public final void output( final PipeFileParser parser ) {
		switch ( fType ) {
		case countLines:
			parser.countLines();
			break;
		case file:
			parser.toFile( fFilename, fSeparator );
			break;
		case stdout:
			parser.output();
			break;
		default:
			throw new Error( "Invalid type - " + fType );
		}
	}

	public final Type type() {
		return fType;
	}

	public final boolean usesStdout() {
		final boolean stdout;
		switch ( fType ) {
		case countLines:
			stdout = true;
			break;
		case file:
			stdout = false;
			break;
		case stdout:
			stdout = true;
			break;
		default:
			throw new Error( "Invalid type - " + fType );
		}
		return stdout;
	}
	
}
