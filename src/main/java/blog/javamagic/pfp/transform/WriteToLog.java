package blog.javamagic.pfp.transform;

import java.util.ArrayList;
import java.util.List;

import blog.javamagic.pfp.antlr.Transform.TemplateParameter;
import blog.javamagic.pfp.logger.Logger;
import blog.javamagic.pfp.variable.Variables;

final class WriteToLog implements LineTransform {

	private final int fLogLevel;
	private final String fTemplate;
	private final List<TemplateParameter> fTemplateParameters;

	public WriteToLog(
			final int logLevel,
			final String template,
			final List<TemplateParameter> templateParameters
	) {
		fLogLevel = logLevel;
		fTemplate = template;
		fTemplateParameters = templateParameters;
	}

	@Override
	public final String[] t( final String[] line ) {
		final List<String> params = new ArrayList<>();
		params.add( "" );
		for ( final TemplateParameter param : fTemplateParameters ) {
			params.add(
					param.column != null
					? line[param.column]
							: Variables.getString( param.variable )
			);
		}
		final int params_count = fTemplateParameters.size();
		final String[] temp_line =
				params.toArray( new String[params_count + 1] );
		final int[] columns = new int[params_count];
		for ( int i = 0; i < params_count; ++i ) {
			columns[i] = i + 1;
		}
		final String[] new_line =
				LineTransforms
				.template( 0, fTemplate, columns )
				.t( temp_line );
		Logger.log(
				fLogLevel,
				() -> new_line[0],
				() -> null
		);
		return line;
	}

}
