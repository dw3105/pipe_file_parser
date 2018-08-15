package blog.javamagic.pfp.transform;

import java.util.Arrays;

final class TemplateReplacer implements LineTransform {

	private final int fTargetColumn;
	private final String fTemplate;
	private final int[] fParamColumns;

	public TemplateReplacer(
			final int targetColumn,
			final String template,
			final int[] paramColumns
	) {
		fTargetColumn = targetColumn;
		fTemplate = parseTemplate( template );
		fParamColumns = paramColumns;
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
		final Object[] params = new Object[fParamColumns.length];
		for ( int i = 0; i < fParamColumns.length; ++i ) {
			params[i] = line[fParamColumns[i]];
		}
		new_line[fTargetColumn] = String.format( fTemplate, params );
		return new_line;
	}

}
