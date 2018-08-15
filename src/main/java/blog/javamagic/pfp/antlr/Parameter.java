package blog.javamagic.pfp.antlr;

public final class Parameter extends AbstractContainer {

	public enum Type {
		integer,
		string,
		bool,
		variable		
	}

	private Type fType;
	private int fIntValue;
	private String fStringValue;
	private boolean fBooleanValue;
	private String fVariable;

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void setInteger( final int value ) {
		fIntValue = value;
	}

	public final void setString( final String value ) {
		fStringValue = PFPSyntax.string( value );
	}

	public final void setBoolean( final boolean bool ) {
		fBooleanValue = bool;
	}

	public final void setVariable( final String variable ) {
		fVariable = variable;
	}

	public final int integer() {
		return fIntValue;
	}

	public final String variable() {
		return fVariable;
	}

	public final boolean bool() {
		return fBooleanValue;
	}

	public final Type type() {
		return fType;
	}

	public final String string() {
		return fStringValue;
	}

}
