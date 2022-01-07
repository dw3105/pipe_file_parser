package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.parser.PipeFileParser;
import blog.javamagic.pfp.transform.LineTransforms;
import blog.javamagic.pfp.variable.Variables;

public final class VarDefinitions extends AbstractContainer {
	
	public static final class Parameter {

		private final String string;
		private final String variable;

		public Parameter( final String string, final String variable ) {
			if ( string != null ) {
				this.string = string;
			}
			else {
				this.string = null;
			}
			this.variable = variable;
		}

	}

	private static final class VarDefinition {

		private final String name;
		private final Integer intValue;
		private final String stringValue;
		private final Integer column;
		private final Parser parser;
		private final String template;
		private final List<Parameter> parameters;

		public VarDefinition( final String name, final int intValue ) {
			this.name = name;
			this.intValue = intValue;
			this.stringValue = null;
			this.parser = null;
			this.column = null;
			this.template = null;
			this.parameters = null;
		}

		public VarDefinition( final String name, final String stringValue ) {
			this.name = name;
			this.intValue = null;
			this.stringValue = stringValue;
			this.parser = null;
			this.column = null;
			this.template = null;
			this.parameters = null;
		}

		public VarDefinition( final String name, final Parser parser ) {
			this.name = name;
			this.intValue = null;
			this.stringValue = null;
			this.parser = parser;
			this.column = null;
			this.template = null;
			this.parameters = null;
		}

		public VarDefinition(
				final String name,
				final int ignore,
				final int column
		) {
			this.name = name;
			this.intValue = null;
			this.stringValue = null;
			this.parser = null;
			this.column = column;
			this.template = null;
			this.parameters = null;
		}

		public VarDefinition(
				final String name,
				final String template,
				final List<Parameter> parameters
		) {
			this.name = name;
			this.intValue = null;
			this.stringValue = null;
			this.parser = null;
			this.column = null;
			this.template = template;
			this.parameters = parameters;
		}

	}

	private final List<VarDefinition> fVars;
	
	public VarDefinitions() {
		fVars = new ArrayList<>();
	}

	public final void addVar( final String name, final int intValue ) {
		fVars.add( new VarDefinition( name, intValue ) );
	}

	public final void addVar( final String name, final String stringValue ) {
		fVars.add( new VarDefinition( name, stringValue ) );
	}

	public final void addVar( final String name, final Parser parser ) {
		fVars.add( new VarDefinition( name, parser ) );
	}

	public final void addColumnVar( final String name, final int column ) {
		fVars.add( new VarDefinition( name, 0, column ) );
	}

	public final void defineVars() {
		for ( final VarDefinition var_def : fVars ) {
			final String name = var_def.name;
			if ( var_def.intValue != null ) {
				Variables.set( name, var_def.intValue.intValue() );
			}
			if ( var_def.stringValue != null ) {
				Variables.set( name, var_def.stringValue );
			}
			if ( var_def.parser != null ) {
				final String[] cur_line = PFP.currentLine();
				final PipeFileParser parser = var_def.parser.pipeFileParser();
				final StringBuilder sb = new StringBuilder();
				final AtomicInteger counter = new AtomicInteger( 0 );
				parser.parse(
						( line ) -> {
							final int len = line.length;
							if ( len > 0 ) {
								for ( int i = 0; i < len; ++i ) {
									sb.append( line[i] );
								}
								if ( counter.get() > 0 ) {
									sb.append( System.lineSeparator() );
								}
								counter.incrementAndGet();
							}
						}
				);
				final String value = sb.toString();
				Variables.set( name, value );
				PFP.setCurrentLine( cur_line );
			}
			if ( var_def.column != null ) {
				Variables.setColumn( name, var_def.column );
			}
			if ( var_def.template != null ) {
				final List<String> params = new ArrayList<>();
				params.add( "" );
				for ( final Parameter param : var_def.parameters ) {
					params.add(
							param.string != null
							? param.string
									: Variables.getString( param.variable )
					);
				}
				final int params_count = var_def.parameters.size();
				final String[] line =
						params.toArray( new String[params_count + 1] );
				final int[] columns = new int[params_count];
				for ( int i = 0; i < params_count; ++i ) {
					columns[i] = i + 1;
				}
				final String[] new_line =
						LineTransforms
						.template( 0, var_def.template, columns )
						.t( line );
				Variables.set( name, new_line[0] );
			}
		}
	}

	public final void addTemplateVar(
			final String name,
			final String template,
			final List<Parameter> parameters
	) {
		fVars.add( new VarDefinition( name, template, parameters ) );
	}

}
