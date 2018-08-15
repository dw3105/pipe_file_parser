package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.PFP;

public final class Log extends AbstractContainer {
	
	public enum Type {
		file,
		stdout,
		stderr
	}

	private int fLogLevel = PFP.LOG_LEVEL_ERROR;
	private String fFilename;
	private Type fType;

	public final void setLogLevel( final int logLevel ) {
		fLogLevel = logLevel;
	}

	@Override
	protected final void execute() {
		PFP.setLogLevel( fLogLevel );
		switch ( fType ) {
		case file:
			PFP.setLogOutput( fFilename );
			break;
		case stderr:
			PFP.setStderrLogOutput();
			break;
		case stdout:
			PFP.setStdoutLogOutput();
			break;
		default:
			throw new Error( "Invalid type - " + fType );		
		}
	}

	public final void setFileOutput( final String filename ) {
		fType = Type.file;
		fFilename = PFPSyntax.filename( filename );
	}

	public final void setStdoutOutput() {
		fType = Type.stdout;
	}

	public final void setStderrOutput() {
		fType = Type.stderr;
	}

}
