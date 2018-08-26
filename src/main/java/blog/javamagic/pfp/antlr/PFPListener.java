package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxBaseListener;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_begins_withContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_class_nameContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_commandContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_containsContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_dictionaryContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_ends_withContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_filterContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_ifContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_if_branchContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_import_filterContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_import_transformContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_inputContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_logContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_log_outputContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_outputContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_parameterContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_parametersContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_parserContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_predicateContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_programContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_rangeContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_stringContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_string_compContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_string_or_varContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_templateContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_template_paramContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_transformContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_var_defContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_var_defsContext;
import blog.javamagic.pfp.antlr.generated.PFPSyntaxParser.Pfp_write_to_logContext;

public final class PFPListener extends PFPSyntaxBaseListener {

	private Program fProgram;
	private PFPContainer fCurrentContainer;

	@Override
	public void enterPfp_program( Pfp_programContext ctx ) {
		ctx.result = new Program();
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_log( Pfp_logContext ctx ) {
		ctx.result = new Log();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_command( Pfp_commandContext ctx ) {
		ctx.result = new Command();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_output( Pfp_outputContext ctx ) {
		ctx.result = new Output();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_import_filter( Pfp_import_filterContext ctx ) {
		ctx.result = new ImportFilter();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_class_name( Pfp_class_nameContext ctx ) {
		ctx.result = ctx.getText();
	}

	@Override
	public void enterPfp_import_transform( Pfp_import_transformContext ctx ) {
		ctx.result = new ImportTransform();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_dictionary( Pfp_dictionaryContext ctx ) {
		ctx.result = new Dictionary();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_parser( Pfp_parserContext ctx ) {
		ctx.result = new Parser();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_input( Pfp_inputContext ctx ) {
		ctx.result = new Input();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_filter( Pfp_filterContext ctx ) {
		ctx.result = new Filter();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_predicate( Pfp_predicateContext ctx ) {
		ctx.result = new Predicate();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_parameters( Pfp_parametersContext ctx ) {
		ctx.result = new Parameters();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_parameter( Pfp_parameterContext ctx ) {
		ctx.result = new Parameter();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void enterPfp_transform( Pfp_transformContext ctx ) {
		ctx.result = new Transform();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void exitPfp_program( Pfp_programContext ctx ) {
		fProgram = ctx.result;
		fCurrentContainer = null;
	}

	@Override
	public void exitPfp_log( Pfp_logContext ctx ) {
		final Log log = ctx.result;
		fCurrentContainer = log.parent();
		final Pfp_log_outputContext output = ctx.pfp_log_output();
		if ( output.pfp_short_file() != null ) {
			log.setFileOutput( output.pfp_short_file().STRING().getText() );
		}
		if ( output.FILE() != null ) {
			log.setFileOutput( output.STRING().getText() );
		}
		if ( output.STDOUT() != null ) {
			log.setStdoutOutput();
		}
		if ( output.STDERR() != null ) {
			log.setStderrOutput();
		}
		if ( ctx.ALL() != null ) {
			log.setLogLevel( PFP.LOG_LEVEL_ALL );
		}
		if ( ctx.DEBUG() != null ) {
			log.setLogLevel( PFP.LOG_LEVEL_DEBUG );
		}
		if ( ctx.INFO() != null ) {
			log.setLogLevel( PFP.LOG_LEVEL_INFO );
		}
		if ( ctx.WARNING() != null ) {
			log.setLogLevel( PFP.LOG_LEVEL_WARNING );
		}
		if ( ctx.ERROR() != null ) {
			log.setLogLevel( PFP.LOG_LEVEL_ERROR );
		}
		if ( ctx.DEBUG() != null ) {
			log.setLogLevel( PFP.LOG_LEVEL_DEBUG );
		}
	}

	@Override
	public void exitPfp_command( Pfp_commandContext ctx ) {
		final Command command = ctx.result;
		fCurrentContainer = command.parent();
		command.setParser( ctx.pfp_parser().result );
		if ( ctx.pfp_output() != null ) {
			command.setOutput( ctx.pfp_output().result );
		}
	}

	@Override
	public void exitPfp_output( Pfp_outputContext ctx ) {
		final Output output = ctx.result;
		fCurrentContainer = output.parent();
		if ( ctx.pfp_short_file() != null ) {
			output.setType( Output.Type.file );
			output.setFilename( ctx.pfp_short_file().STRING().getText() );
		}
		if ( ctx.FILE() != null ) {
			output.setType( Output.Type.file );
			output.setFilename( ctx.STRING( 0 ).getText() );
			if ( ctx.STRING().size() == 2 ) {
				output.setSeparator( ctx.STRING( 1 ).getText() );
			}
		}
		if ( ctx.STDOUT() != null ) {
			output.setType( Output.Type.stdout );
		}
		if ( ctx.COUNT_LINES() != null ) {
			output.setType( Output.Type.countLines );
		}
	}

	@Override
	public void exitPfp_import_filter( Pfp_import_filterContext ctx ) {
		final ImportFilter import_filter = ctx.result;
		fCurrentContainer = import_filter.parent();
		import_filter.setClassName( ctx.pfp_class_name().result );
		if ( ctx.VAR() != null ) {
			import_filter.setName( ctx.VAR().getText() );
		}
	}

	@Override
	public void exitPfp_import_transform( Pfp_import_transformContext ctx ) {
		final ImportTransform import_transform = ctx.result;
		fCurrentContainer = import_transform.parent();
		import_transform.setClassName( ctx.pfp_class_name().result );
		if ( ctx.VAR() != null ) {
			import_transform.setName( ctx.VAR().getText() );
		}
	}

	@Override
	public void exitPfp_dictionary( Pfp_dictionaryContext ctx ) {
		final Dictionary dictionary = ctx.result;
		fCurrentContainer = dictionary.parent();
		dictionary.setName( ctx.VAR().getText() );
		dictionary.setParser( ctx.pfp_parser().result );
		if ( ctx.INT() != null ) {
			dictionary.setIndexColumn( Integer.parseInt( ctx.INT().getText() ) );
		}
	}

	@Override
	public void exitPfp_parser( Pfp_parserContext ctx ) {
		final Parser parser = ctx.result;
		fCurrentContainer = parser.parent();
		parser.setInput( ctx.pfp_input().result );
		if ( ctx.UNIQUE() != null ) {
			parser.unique();
		}
	}

	@Override
	public void exitPfp_input( Pfp_inputContext ctx ) {
		final Input input = ctx.result;
		fCurrentContainer = input.parent();
		if ( ctx.pfp_short_file() != null ) {
			input.setType( Input.Type.files );
			input.addFilemask( ctx.pfp_short_file().STRING().getText() );
		}
		if ( ctx.FILE() != null ) {
			input.setType( Input.Type.file );
			input.setFilename( ctx.STRING( 0 ).getText() );
		}
		if ( ctx.FILES() != null ) {
			input.setType( Input.Type.files );
			for ( TerminalNode str : ctx.STRING() ) {
				input.addFilemask( str.getText() );
			}
			if ( ctx.pfp_parser() != null ) {
				input.setParser( ctx.pfp_parser().result );
			}
		}
		if ( ctx.FILENAMES() != null ) {
			input.setType( Input.Type.fileNames );
			for ( TerminalNode str : ctx.STRING() ) {
				input.addFilemask( str.getText() );
			}
		}
		if ( ctx.STDIN() != null ) {
			input.setType( Input.Type.stdin );
		}
		if ( ctx.PARSER() != null ) {
			input.setType( Input.Type.parser );
			input.setParser( ctx.pfp_parser().result );
		}
		if ( ctx.HEAD() != null ) {
			input.setType( Input.Type.head );
			input.setParser( ctx.pfp_parser().result );
			if ( ctx.INT() != null ) {
				input.setLinesCount( Integer.parseInt( ctx.INT().getText() ) );
			}
		}
		if ( ctx.TAIL() != null ) {
			input.setType( Input.Type.tail );
			input.setParser( ctx.pfp_parser().result );
			if ( ctx.INT() != null ) {
				input.setLinesCount( Integer.parseInt( ctx.INT().getText() ) );
			}
		}
		if ( ctx.VAR() != null ) {
			input.setType( Input.Type.dictionary );
			input.setVariable( ctx.VAR().getText() );
		}
	}

	@Override
	public void exitPfp_filter( Pfp_filterContext ctx ) {
		final Filter filter = ctx.result;
		fCurrentContainer = filter.parent();
		if ( ctx.pfp_include() != null ) {
			filter.setType( Filter.Type.include );
			filter.setPredicate( ctx.pfp_include().pfp_predicate().result );
		}
		if ( ctx.pfp_exclude() != null ) {
			filter.setType( Filter.Type.exclude );
			filter.setPredicate( ctx.pfp_exclude().pfp_predicate().result );
		}
	}

	@Override
	public void exitPfp_predicate( Pfp_predicateContext ctx ) {
		final Predicate predicate = ctx.result;
		fCurrentContainer = predicate.parent();
		if ( ctx.pfp_predicate() != null ) {
			ctx.result = ctx.pfp_predicate().result;
		}
		if ( ctx.pfp_string_comp() != null ) {
			predicate.setType( Predicate.Type.string_comparison );
			final Pfp_string_compContext comp = ctx.pfp_string_comp();
			final Pfp_string_or_varContext left = comp.pfp_string_or_var( 0 );
			if ( left.STRING() != null ) {
				predicate.setLeftString( left.STRING().getText() );
			}
			if ( left.VAR() != null ) {
				predicate.setLeftVariable( left.VAR().getText() );
			}
			final Pfp_string_or_varContext right = comp.pfp_string_or_var( 1 );
			if ( right.STRING() != null ) {
				predicate.setRightString( right.STRING().getText() );
			}
			if ( right.VAR() != null ) {
				predicate.setRightVariable( right.VAR().getText() );
			}
			if ( comp.EQ() != null ) {
				predicate.setComparison( Predicate.Comparison.equals );
			}
			if ( comp.NE() != null ) {
				predicate.setComparison( Predicate.Comparison.not_equals );
			}
		}
		if ( ctx.pfp_contains() != null ) {
			predicate.setType( Predicate.Type.contains );
			final Pfp_containsContext contains = ctx.pfp_contains();
			for (
					final Pfp_string_or_varContext str_var
					: contains.pfp_string_or_var()
			) {			
				if ( str_var.STRING() != null ) {
					predicate.addStringParam( str_var.STRING().getText() );
				}
				if ( str_var.VAR() != null ) {
					predicate.addVariableParam( str_var.VAR().getText() );
				}
			}
			if ( contains.INT() != null ) {
				predicate.setColumn( Integer.parseInt( contains.INT().getText() ) );
			}
		}
		if ( ctx.pfp_begins_with() != null ) {
			predicate.setType( Predicate.Type.beginsWith );
			final Pfp_begins_withContext begins_with = ctx.pfp_begins_with();
			for (
					final Pfp_string_or_varContext str_var
					: begins_with.pfp_string_or_var()
			) {			
				if ( str_var.STRING() != null ) {
					predicate.addStringParam( str_var.STRING().getText() );
				}
				if ( str_var.VAR() != null ) {
					predicate.addVariableParam( str_var.VAR().getText() );
				}
			}
			if ( begins_with.INT() != null ) {
				predicate.setColumn( Integer.parseInt( begins_with.INT().getText() ) );
			}
		}
		if ( ctx.pfp_ends_with() != null ) {
			predicate.setType( Predicate.Type.endsWith );
			final Pfp_ends_withContext ends_with = ctx.pfp_ends_with();
			for (
					final Pfp_string_or_varContext str_var
					: ends_with.pfp_string_or_var()
			) {			
				if ( str_var.STRING() != null ) {
					predicate.addStringParam( str_var.STRING().getText() );
				}
				if ( str_var.VAR() != null ) {
					predicate.addVariableParam( str_var.VAR().getText() );
				}
			}
			if ( ends_with.INT() != null ) {
				predicate.setColumn( Integer.parseInt( ends_with.INT().getText() ) );
			}
		}
		if ( ctx.IN_DICTIONARY() != null ) {
			predicate.setType( Predicate.Type.inDictionary );
			predicate.setParameters( ctx.pfp_parameters().result );
		}
		if ( ctx.VAR() != null ) {
			predicate.setType( Predicate.Type.custom );
			predicate.setName( ctx.VAR().getText() );
			if ( ctx.pfp_parameters() != null ) {
				predicate.setParameters( ctx.pfp_parameters().result );
			}
		}
		if ( ctx.LOOKUP() != null ) {
			predicate.setType( Predicate.Type.lookup );
			predicate.setDictionary( ctx.VAR().getText() );
			if ( ctx.INT() != null ) {
				predicate.setColumn( Integer.parseInt( ctx.INT().getText() ) );
			}
		}
	}

	@Override
	public void exitPfp_parameters( Pfp_parametersContext ctx ) {
		final Parameters parameters = ctx.result;
		fCurrentContainer = parameters.parent();
		for ( Pfp_parameterContext param : ctx.pfp_parameter() ) {
			parameters.addParameter( param.result );
		}
	}

	@Override
	public void exitPfp_parameter( Pfp_parameterContext ctx ) {
		final Parameter parameter = ctx.result;
		fCurrentContainer = parameter.parent();
		if ( ctx.INT() != null ) {
			parameter.setType( Parameter.Type.integer );
			parameter.setInteger( Integer.parseInt( ctx.INT().getText() ) );
		}
		if ( ctx.STRING() != null ) {
			parameter.setType( Parameter.Type.string );
			parameter.setString( ctx.STRING().getText() );
		}
		if ( ctx.BOOL() != null ) {
			parameter.setType( Parameter.Type.bool );
			parameter.setBoolean( Boolean.parseBoolean( ctx.BOOL().getText() ) );
		}
		if ( ctx.VAR() != null ) {
			parameter.setType( Parameter.Type.variable );
			parameter.setVariable( ctx.VAR().getText() );
		}
	}

	@Override
	public void exitPfp_transform( Pfp_transformContext ctx ) {
		final Transform transform = ctx.result;
		fCurrentContainer = transform.parent();
		if ( ctx.pfp_if() != null ) {
			transform.setType( Transform.Type.if_ );
			transform.setIf( ctx.pfp_if().result );
		}
		if ( ctx.BASENAME() != null ) {
			transform.setType( Transform.Type.basename );
			if ( ctx.INT().size() > 0 ) {
				transform.setTargetColumn( Integer.parseInt( ctx.INT( 0 ).getText() ) );
			}
		}
		if ( ctx.COLUMNS() != null ) {
			transform.setType( Transform.Type.columns );
			extractColumns( ctx, transform );
		}
		if ( ctx.LAST_MATCHING_FILE() != null ) {
			transform.setType( Transform.Type.lastMatchingFile );
			if ( ctx.INT().size() > 1 ) {
				transform.setTargetColumn( Integer.parseInt( ctx.INT( 1 ).getText() ) );
			}
			if ( ctx.INT().size() > 0 ) {
				transform.setSourceColumn( Integer.parseInt( ctx.INT( 0 ).getText() ) );
			}
		}
		if ( ctx.MERGE() != null ) {
			transform.setType( Transform.Type.merge );
			if ( ctx.STRING() != null ) {
				transform.setSeparator( ctx.STRING().getText() );
			}
		}
		if ( ctx.REPLACE() != null ) {
			transform.setType( Transform.Type.replace );
			transform.setParameters( ctx.pfp_parameters().result );
		}
		if ( ctx.SPLIT() != null ) {
			transform.setType( Transform.Type.split );
			transform.setSeparator( ctx.STRING().getText() );
		}
		if ( ctx.TEMPLATE() != null ) {
			transform.setType( Transform.Type.template );
			transform.setTargetColumn( Integer.parseInt( ctx.INT( 0 ).getText() ) );
			transform.setTemplate( ctx.STRING().getText() );
			for ( final Pfp_template_paramContext param : ctx.pfp_template_param() ) {
				if ( param.INT() != null ) {
					transform.addTemplateParameter(
							Integer.parseInt( param.INT().getText() )
					);
				}
				if ( param.VAR() != null ) {
					transform.addTemplateParameter( param.VAR().getText() );
				}
			}
		}
		if ( ctx.TO_LOWER_CASE() != null ) {
			transform.setType( Transform.Type.toLowerCase );
			if ( ctx.INT().size() > 0 ) {
				transform.setTargetColumn( Integer.parseInt( ctx.INT( 0 ).getText() ) );
			}
		}
		if ( ctx.VAR() != null ) {
			transform.setType( Transform.Type.custom );
			transform.setName( ctx.VAR().getText() );
			if ( ctx.pfp_parameters() != null ) {
				transform.setParameters( ctx.pfp_parameters().result );
			}
		}
		if ( ctx.pfp_write_to_log() != null ) {
			transform.setType( Transform.Type.writeToLog );
			final Pfp_write_to_logContext log = ctx.pfp_write_to_log();
			transform.setLogTemplate( log.STRING().getText() );
			int log_level = PFP.LOG_LEVEL_ALL;
			//ALL|DEBUG|INFO|WARNING|ERROR
			if ( log.ALL() != null ) {
				log_level = PFP.LOG_LEVEL_ALL;
			}
			if ( log.DEBUG() != null ) {
				log_level = PFP.LOG_LEVEL_DEBUG;
			}
			if ( log.INFO() != null ) {
				log_level = PFP.LOG_LEVEL_INFO;
			}
			if ( log.WARNING() != null ) {
				log_level = PFP.LOG_LEVEL_WARNING;
			}
			if ( log.ERROR() != null ) {
				log_level = PFP.LOG_LEVEL_ERROR;
			}
			transform.setLogLevel( log_level );
			for ( final Pfp_template_paramContext param : log.pfp_template_param() ) {
				if ( param.INT() != null ) {
					transform.addTemplateParameter(
							Integer.parseInt( param.INT().getText() )
					);
				}
				if ( param.VAR() != null ) {
					transform.addTemplateParameter( param.VAR().getText() );
				}
			}
		}
	}
	
	private final static class ColumnsParameter {

		private final int index;
		private final TerminalNode col;
		private final Pfp_rangeContext range;

		public ColumnsParameter( final TerminalNode col ) {
			index = col.getSymbol().getTokenIndex();
			this.col = col;
			this.range = null;
		}

		public ColumnsParameter( final Pfp_rangeContext range ) {
			index = range.INT( 0 ).getSymbol().getTokenIndex();
			this.col = null;
			this.range = range;
		}
		
	}

	private final void extractColumns(
			final Pfp_transformContext ctx,
			final Transform transform
	) {
		final List<ColumnsParameter> params = new ArrayList<>();
		for ( final TerminalNode col : ctx.INT() ) {
			params.add( new ColumnsParameter( col ) );
		}
		for ( final Pfp_rangeContext range : ctx.pfp_range() ) {
			params.add( new ColumnsParameter( range ) );
		}
		Collections.sort( params, new Comparator<ColumnsParameter>() {

			@Override
			public int compare( ColumnsParameter p1, ColumnsParameter p2 ) {
				return Integer.compare( p1.index, p2.index );
			}
		} );
		for ( final ColumnsParameter parameter : params ) {
			if ( parameter.col != null ) {
				transform.addColumn(
						Integer.parseInt( parameter.col.getText() )
				);
			}
			else {
				transform.addColumnsRange(
						Integer.parseInt( parameter.range.INT( 0 ).getText() ),
						Integer.parseInt( parameter.range.INT( 1 ).getText() )
				);
			}
		}
	}

	@Override
	public void enterPfp_var_defs( Pfp_var_defsContext ctx ) {
		ctx.result = new VarDefinitions();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void exitPfp_var_defs( Pfp_var_defsContext ctx ) {
		final VarDefinitions var_defs = ctx.result;
		fCurrentContainer = var_defs.parent();
		for ( final Pfp_var_defContext var_def : ctx.pfp_var_def() ) {
			final String name = var_def.VAR().getText();
			if ( var_def.INT() != null ) {
				var_defs.addVar(
						name,
						Integer.parseInt( var_def.INT().getText() )
				);
			}
			if ( var_def.pfp_string() != null ) {
				final Pfp_stringContext str = var_def.pfp_string();
				if ( str.pfp_template() != null ) {
					final Pfp_templateContext tmpl = str.pfp_template();
					final String tmpl_str = tmpl.STRING().getText();
					final List<VarDefinitions.Parameter> parameters =
							new ArrayList<>();
					for (
							final Pfp_string_or_varContext param
							: tmpl.pfp_string_or_var()
					) {
						VarDefinitions.Parameter parameter = null;
						if ( param.STRING() != null ) {
							parameter =
									new VarDefinitions.Parameter(
											param.STRING().getText(),
											null
									);
						}
						if ( param.VAR() != null ) {
							parameter =
									new VarDefinitions.Parameter(
											null,
											param.VAR().getText()
									);
						}
						parameters.add( parameter );
					}
					var_defs.addTemplateVar( name, tmpl_str, parameters );
				}
				if ( str.STRING() != null ) {
					var_defs.addVar( name, str.STRING().getText() );
				}
				if ( str.INT() != null ) {
					var_defs.addColumnVar(
							name,
							Integer.parseInt( str.INT().getText() )
					);
				}
			}
			if ( var_def.pfp_parser() != null ) {
				var_defs.addVar( name, var_def.pfp_parser().result );
			}
		}
	}

	@Override
	public void enterPfp_if( Pfp_ifContext ctx ) {
		ctx.result = new If();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void exitPfp_if( Pfp_ifContext ctx ) {
		final If if_ = ctx.result;
		fCurrentContainer = if_.parent();
		if_.setPredicate( ctx.pfp_predicate().result );
		if_.setMainBranch( ctx.pfp_if_branch( 0 ).result );
		if ( ctx.pfp_if_branch().size() == 2 ) {
			if_.setAlternativeBranch( ctx.pfp_if_branch( 1 ).result );
		}
	}

	@Override
	public void enterPfp_if_branch( Pfp_if_branchContext ctx ) {
		ctx.result = new IfBranch();
		fCurrentContainer.add( ctx.result );
		fCurrentContainer = ctx.result;
	}

	@Override
	public void exitPfp_if_branch( Pfp_if_branchContext ctx ) {
		final IfBranch if_branch = ctx.result;
		fCurrentContainer = if_branch.parent();
		// TODO Auto-generated method stub
	}

	@Override
	public void visitErrorNode( ErrorNode node ) {
		throw new RuntimeException( "Error in pfp expression: " + node.getText() );
	}

	public Program program() {
		return fProgram;
	}

}
