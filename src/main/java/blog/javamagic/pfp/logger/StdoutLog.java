package blog.javamagic.pfp.logger;

final class StdoutLog implements LogOutput {

	@Override
	public final void println( final String message ) {
		System.out.println( message );
	}

	@Override
	public final void close() throws Exception {
		// do nothing
	}

	@Override
	public final boolean usesStdout() {
		return true;
	}

}
