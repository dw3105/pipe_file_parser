package blog.javamagic.pfp.logger;

import java.io.FileWriter;

final class FileLog implements LogOutput {
	
	private final FileWriter fWriter;
	private final String fFilename;

	public FileLog( final String filename ) {
		fFilename = filename;
		FileWriter writer;
		try {
			writer = new FileWriter( filename );
		}
		catch ( Throwable e ) {
			System.err.println(
					"Cannot write to log file '"
							+ fFilename
							+ "': "
							+ e.getMessage()
			);
			e.printStackTrace();
			writer = null;
		}
		fWriter = writer;
	}

	@Override
	public final void println( final String message ) {
		if ( fWriter != null ) {
			try {
				fWriter.write( message );
				fWriter.write( System.lineSeparator() );
			}
			catch ( Throwable e ) {
				System.err
						.println(
								"Cannot write to log file '"
										+ fFilename
										+ "': "
										+ e.getMessage()
						);
				e.printStackTrace();
			}
		}
	}

	@Override
	public final void close() throws Exception {
		fWriter.close();
	}

	@Override
	public final boolean usesStdout() {
		return false;
	}

}
