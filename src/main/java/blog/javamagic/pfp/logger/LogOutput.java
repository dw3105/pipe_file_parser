package blog.javamagic.pfp.logger;

public interface LogOutput extends AutoCloseable {

	void println( String message );
	boolean usesStdout();

}
