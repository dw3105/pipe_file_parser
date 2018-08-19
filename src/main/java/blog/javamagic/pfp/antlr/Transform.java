package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import blog.javamagic.pfp.parser.PipeFileParser;
import blog.javamagic.pfp.transform.ImportedTransform;
import blog.javamagic.pfp.transform.LineTransform;
import blog.javamagic.pfp.transform.LineTransforms;

public final class Transform extends AbstractContainer {

	public enum Type {
		basename,
		columns,
		lastMatchingFile,
		merge,
		replace,
		split,
		template,
		toLowerCase,
		custom
	}

	private Type fType;
	private String fSeparator = "";
	private int fSourceColumn = 0;
	private int fTargetColumn = 0;
	private String fTemplate;
	private String fName;
	private Parameters fParameters = new Parameters();
	
	private final List<Integer> fColumns;
	
	public Transform() {
		fColumns = new ArrayList<>();
	}

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void addColumn( final int column ) {
		fColumns.add( column );
	}

	public final void setTargetColumn( final int column ) {
		fTargetColumn = column;
	}

	public final void setSourceColumn( final int column ) {
		fSourceColumn = column;
	}

	public final void setParameters( final Parameters parameters ) {
		fParameters = parameters;
	}

	public final void setSeparator( final String separator ) {
		fSeparator = PFPSyntax.string( separator );
	}

	public final void setTemplate( final String template ) {
		fTemplate = PFPSyntax.string( template );
	}

	public final void appendTo( final PipeFileParser parser ) {
		final Function<String[], String[]> transform;
		switch ( fType ) {
		case basename:
			transform = LineTransforms.basename( fTargetColumn )::t;
			break;
		case columns:
			transform = LineTransforms.columns( columns() )::t;
			break;
		case custom:
			final ImportedTransform imp_transform =
					LineTransforms.importedTransform( fName );
			if ( fParameters != null ) {
				imp_transform.setParameters( fParameters );
			}
			transform = imp_transform::t;
			break;
		case lastMatchingFile:
			transform =
					LineTransforms.lastMatchingFile(
							fTargetColumn,
							fSourceColumn
					)::t;
			break;
		case merge:
			transform = LineTransforms.merge( fSeparator )::t;
			break;
		case replace:
			transform = replaceTransform()::t;
			break;
		case split:
			transform = LineTransforms.split( fSeparator )::t;
			break;
		case template:
			transform =
					LineTransforms.template(
							fTargetColumn,
							fTemplate,
							columns()
					)::t;
			break;
		case toLowerCase:
			transform = LineTransforms.toLowerCase( fTargetColumn )::t;
			break;
		default:
			throw new Error( "Invalid type - " + fType );
		}
		parser.transform( transform );
	}

	private final LineTransform replaceTransform() {
		final LineTransform replace;
		switch ( fParameters.count() ) {
		case 1:
			{
				final Parameter parameter0 = fParameters.parameter( 0 );
				if ( parameter0.type() == Parameter.Type.string ) {
					replace = LineTransforms.replace( parameter0.string() );
				}
				else {
					throw new RuntimeException( "Invalid parameter #0" );
				}
			}
			break;
		case 2:
			{
				final Parameter parameter0 = fParameters.parameter( 0 );
				final Parameter parameter1 = fParameters.parameter( 1 );
				if ( parameter0.type() == Parameter.Type.string ) {
					final String source = parameter0.string();
					if ( parameter1.type() == Parameter.Type.string ) {
						final String replacement = parameter1.string();
						replace = LineTransforms.replace( source, replacement );
					}
					else {
						throw new RuntimeException( "Invalid parameter #1" );
					}
				}
				else {
					throw new RuntimeException( "Invalid parameter #0" );
				}
			}
			break;
		default:
			throw new RuntimeException( "Invalid parameters" );
		}
		return replace;
	}

	private final int[] columns() {
		final int size = fColumns.size();
		final int[] cols = new int[size];
		for ( int i = 0; i < size; ++i ) {
			cols[i] = fColumns.get( i ).intValue();
		}
		return cols;
	}

	public final void setName( final String name ) {
		fName = name;
	}

	public final void addColumnsRange( final int fromCol, final int toCol ) {
		for ( int col = fromCol; col <= toCol; ++col ) {
			addColumn( col );
		}
	}

}
