package blog.javamagic.pfp.antlr;

interface PFPContainer {
	
	void add( PFPContainer container );
	void setParent( PFPContainer abstractContainer );
	PFPContainer parent();

}
