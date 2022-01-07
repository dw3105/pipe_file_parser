package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.parser.PipeFileParser;

public final class Output extends AbstractContainer {

	public enum Type {
		csv,
		file,
		stdout
	}

	private Type fType;
	private String fFilename;
	private String fSeparator = "";
	private boolean fAppend = false;

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void setFilename( final String filename ) {
		fFilename = PFPSyntax.filename( filename );
	}

	public final void setSeparator( final String separator ) {
		fSeparator = separator;
	}

	public final void output( final PipeFileParser parser ) {
		switch ( fType ) {
		case csv:
			parser.toCsv( fFilename );
			break;
		case file:
			if ( fAppend ) {
				parser.appendFile( fFilename, fSeparator );
			}
			else {
				parser.toFile( fFilename, fSeparator );
			}
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
		case csv:
			stdout = false;
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

	public final void append( final boolean append ) {
		fAppend  = append;
	}
	
}
