package blog.javamagic.pfp.transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import blog.javamagic.pfp.antlr.Transform.TemplateParameter;
import blog.javamagic.pfp.variable.Variables;

final class TemplateReplacer implements LineTransform {

	private final int fTargetColumn;
	private final String fTemplate;
	private final List<TemplateParameter> fParameters;

	public TemplateReplacer(
			final int targetColumn,
			final String template,
			final int[] paramColumns
	) {
		fTargetColumn = targetColumn;
		fTemplate = parseTemplate( template );
		fParameters = new ArrayList<>();
		for ( final int column : paramColumns ) {
			fParameters.add( new TemplateParameter( column ) );
		}
	}

	public TemplateReplacer(
			final int targetColumn,
			final String template,
			final List<TemplateParameter> parameters
	) {
		fTargetColumn = targetColumn;
		fTemplate = parseTemplate( template );
		fParameters = parameters;
	}

	private final static String parseTemplate( final String template ) {
		final StringBuilder sb = new StringBuilder();
		boolean pattern_started = false;
		String pattern_num = "";
		for ( char ch : template.toCharArray() ) {
			switch ( ch ) {
			case '#':
				if ( pattern_started ) {
					if ( pattern_num.length() > 0 ) {
						appendPattern( sb, pattern_num );
						pattern_num = "";
					}
					else {
						sb.append( ch );
						pattern_started = false;
					}
				}
				else {
					pattern_started = true;
					pattern_num = "";
				}
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if ( pattern_started ) {
					pattern_num += ch;
				}
				else {
					sb.append( ch );
				}
				break;
			default:
				if ( pattern_started ) {
					if ( pattern_num.length() > 0 ) {
						appendPattern( sb, pattern_num );
						pattern_started = false;
					}
					else {
						throw new RuntimeException(
								"Invalid template: '"
										+ template
										+ '"'
						);
					}
				}
				sb.append( ch );
			}
		}
		if ( pattern_started ) {
			if ( pattern_num.length() > 0 ) {
				appendPattern( sb, pattern_num );
			}
			else {
				throw new RuntimeException(
						"Invalid template: '"
								+ template
								+ '"'
				);
			}
		}
		return sb.toString();
	}

	private final static void appendPattern(
			final StringBuilder sb,
			final String patternNum
	) {
		final int num = Integer.parseInt( patternNum ) + 1;
		sb.append( "%" ).append( num ).append( "$s" );
	}

	@Override
	public final String[] t( final String[] line ) {
		final String[] new_line = Arrays.copyOf( line, line.length );
		final int params_count = fParameters.size();
		final Object[] params = new Object[params_count];
		for ( int i = 0; i < params_count; ++i ) {
			final TemplateParameter param = fParameters.get( i );
			if ( param.column != null ) {
				params[i] = line[param.column];
			}
			else {
				params[i] = Variables.getString( param.variable );
			}
		}
		new_line[fTargetColumn] = String.format( fTemplate, params );
		return new_line;
	}

}
