package blog.javamagic.pfp.logger;

final class StderrLog implements LogOutput {

	@Override
	public final void close() throws Exception {
		// do nothing
	}

	@Override
	public final void println( final String message ) {
		System.err.println( message );
	}

	@Override
	public final boolean usesStdout() {
		return false;
	}

}
