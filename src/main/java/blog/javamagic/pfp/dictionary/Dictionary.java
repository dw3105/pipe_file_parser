package blog.javamagic.pfp.dictionary;

import blog.javamagic.pfp.source.Source;

public interface Dictionary extends Source {
	
	String[] line( String id );
	
}
