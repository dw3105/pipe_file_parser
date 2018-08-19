package blog.javamagic.pfp.source;

abstract class AbstractSource implements Source {

	private boolean fStopped = false;

	@Override
	public final void stop() {
		fStopped = true;
	}
	
	protected final boolean stopped() {
		return fStopped;
	}

}
