package blog.javamagic.pfp.logger;

import java.util.Arrays;
import java.util.Date;
import java.util.function.Supplier;

import blog.javamagic.pfp.PFP;

public final class Logger {

	private enum MsgType {
		error( "<E>" ),
		warning( "<w>" ),
		info( "<i>" ),
		debug( "<d>" ),
		all( "<a>" );
		
		private final String fLabel;
		
		private MsgType( final String label ) {
			fLabel = label;
		}

		private final static MsgType get( final int logLevel ) {
			final MsgType type;
			switch ( logLevel ) {
			case PFP.LOG_LEVEL_ERROR:
				type = error;
				break;
			case PFP.LOG_LEVEL_WARNING:
				type = warning;
				break;
			case PFP.LOG_LEVEL_INFO:
				type = info;
				break;
			case PFP.LOG_LEVEL_DEBUG:
				type = debug;
				break;
			case PFP.LOG_LEVEL_ALL:
				type = all;
				break;
			default:
				throw new Error( "Unexpected log level: " + logLevel );
			}
			return type;
		}

		public final String label() {
			return fLabel;
		}
	}

	private final static String DEFAULT_LOG_TEMPLATE =
			"%1$s\t%2$tY-%2$tm-%2$td %2$tH:%2$tM:%2$tS.%2$tL\t\t%3$s";

	private static String fLogTemplate = DEFAULT_LOG_TEMPLATE;
	private static boolean fErrorCaught = false;

	public final static void log(
			final int logLevel,
			final Supplier<String> templateSource,
			final Supplier<Object[]> paramsSource
	) {
		if ( logLevel == PFP.LOG_LEVEL_ERROR ) {
			setErrorCaught( true );
		}
		if ( PFP.logEnabled( logLevel ) ) {
			final String template;
			try {
				template = templateSource.get();
			}
			catch ( Throwable e ) {
				if ( PFP.logEnabled( PFP.LOG_LEVEL_ERROR ) ) {
					logMessage(
							MsgType.error,
							"Exception caught while fetching template: "
									+ e.getMessage(),
							fLogTemplate
					);
				}
				return;
			}
			final Object[] params;
			try {
				params = paramsSource.get();
			}
			catch ( Exception e ) {
				if ( PFP.logEnabled( PFP.LOG_LEVEL_ERROR ) ) {
					logMessage(
							MsgType.error,
							"Exception caught while fetching parameters: "
									+ e.getMessage(),
							fLogTemplate
					);
				}
				return;
			}
			try {
				final String msg = String.format( template, params );
				logMessage( MsgType.get( logLevel ), msg, fLogTemplate );
			}
			catch ( Throwable e ) {
				if ( PFP.logEnabled( PFP.LOG_LEVEL_ERROR ) ) {
					final String safe_template;
					if ( template == null ) {
						safe_template = template;
					}
					else {
						safe_template = "null";
					}
					final String safe_params =
							(
									params != null
											? Arrays.toString( params )
											: "null"
							);
					logMessage(
							MsgType.error,
							"Exception caught while formatting template '"
									+ safe_template
									+ "' with parameters "
									+ safe_params
									+ ": "
									+ e.getMessage(),
							fLogTemplate
					);
				}
			}
		}
	}

	private final static void logMessage(
			final MsgType type,
			final String message,
			final String logTemplate
	) {
		if ( type == MsgType.error ) {
			setErrorCaught( true );
		}
		try {
			PFP.logOutput().println(
					String.format(
							logTemplate,
							type.label(),
							new Date(),
							message
					)
			);
		}
		catch ( Throwable e ) {
			if ( !DEFAULT_LOG_TEMPLATE.equals( logTemplate ) ) {
				logMessage(
						MsgType.error,
						"Exception caught while parsing log template '"
										+ logTemplate
										+ "': "
										+ e.getMessage(),
						DEFAULT_LOG_TEMPLATE
				);
			}
			else {
				// this shouldn't happen
				throw new Error(
						"Error in default log template: " + e.getMessage(),
						e
				);
			}
		}
	}

	public final static LogOutput stdout() {
		return new StdoutLog();
	}

	public final static LogOutput file( final String filename ) {
		return new FileLog( filename );
	}

	public final static LogOutput stderr() {
		return new StderrLog();
	}

	public final static boolean errorCaught() {
		return fErrorCaught;
	}

	public final static void setErrorCaught( final boolean value ) {
		fErrorCaught = value;
	}

}
