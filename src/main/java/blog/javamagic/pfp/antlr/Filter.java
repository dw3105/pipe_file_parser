package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.parser.PipeFileParser;

public class Filter extends AbstractContainer {

	public enum Type {
		include,
		exclude
	}

	private Type fType;
	private Predicate fPredicate;

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void setPredicate( final Predicate predicate ) {
		fPredicate = predicate;
	}

	public final void appentTo( final PipeFileParser parser ) {
		if ( fType == Type.include ) {
			parser.include( fPredicate.predicate() );
		}
		else {
			parser.exclude( fPredicate.predicate() );
		}
	}

}
