package blog.javamagic.pfp.source;

import java.util.function.Consumer;

public interface Source {
	
	void forEachLine( Consumer<String[]> consumer );
	void stop();

}
