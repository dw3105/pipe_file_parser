package blog.javamagic.pfp.branch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import blog.javamagic.pfp.antlr.Transform;
import blog.javamagic.pfp.antlr.VarDefinitions;

final class BranchImpl implements Branch {
	
	private static final class BranchElement {

		private final VarDefinitions varDefinitions;
		private final Transform transform;

		public BranchElement( final VarDefinitions varDefinitions ) {
			this.varDefinitions = varDefinitions;
			this.transform = null;
		}

		public BranchElement( final Transform transform ) {
			this.varDefinitions = null;
			this.transform = transform;
		}

	}

	private final List<BranchElement> fElements;
	
	public BranchImpl() {
		fElements = new ArrayList<>();
	}

	@Override
	public final String[] processLine( final String[] line ) {
		String[] new_line = Arrays.copyOf( line, line.length );
		for ( final BranchElement element : fElements ) {
			if ( element.transform != null ) {
				new_line = element.transform.processLine( line );
			}
			else {
				element.varDefinitions.defineVars();
			}
		}
		return new_line;
	}

	@Override
	public final Branch append( final VarDefinitions varDefinitions ) {
		fElements.add( new BranchElement( varDefinitions ) );
		return this;
	}

	@Override
	public final Branch append( final Transform transform ) {
		fElements.add( new BranchElement( transform ) );
		return this;
	}

}
