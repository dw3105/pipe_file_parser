package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;

public final class Parameters extends AbstractContainer {
	
	private final List<Parameter> fParameters;
	
	public Parameters() {
		fParameters = new ArrayList<>();
	}

	public final void addParameter( final Parameter parameter ) {
		fParameters.add( parameter );
	}

	public final int count() {
		return fParameters.size();
	}

	public final Parameter parameter( final int index ) {
		return fParameters.get( index );
	}

}
