package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

abstract class AbstractContainer implements PFPContainer {
	
	private final List<PFPContainer> fChildren;
	private PFPContainer fParent;
	
	public AbstractContainer() {
		fChildren = new ArrayList<>();
		fParent = null;
	}

	@Override
	public final void add( final PFPContainer container ) {
		fChildren.add( container );
		container.setParent( this );
	}

	@Override
	public final void setParent( final PFPContainer parent ) {
		fParent = parent;
	}

	@Override
	public final PFPContainer parent() {
		return fParent;
	}
	
	protected void execute() {
		// by default - do nothing
	}
	
	protected final void forEachChild(
			final Consumer<AbstractContainer> consumer
	) {
		fChildren.forEach(
				( child ) -> consumer.accept( (AbstractContainer) child )
		);
	}

}
