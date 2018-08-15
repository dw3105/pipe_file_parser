package blog.javamagic.pfp.transform;

import blog.javamagic.pfp.antlr.Parameters;

public abstract class ImportedTransform implements LineTransform {

	protected Parameters fParameters;
	
	public void setParameters( final Parameters parameters ) {
		fParameters = parameters;
		validateParameters( parameters );
	}

	abstract protected void validateParameters( Parameters parameters );

	protected void invalidParameters() {
		throw new RuntimeException( "Invalid parameters" );
	}

	protected void invalidParameters( final int index ) {
		throw new RuntimeException( "Invalid parameter #" + index );
	}

}
