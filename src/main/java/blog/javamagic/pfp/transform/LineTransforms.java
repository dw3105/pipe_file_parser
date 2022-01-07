package blog.javamagic.pfp.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import blog.javamagic.pfp.antlr.Transform.TemplateParameter;
import blog.javamagic.pfp.branch.Branch;
import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.variable.VariablesRegistry;

public final class LineTransforms {
	
	private final static Map<String, ImportedTransform> fRegisteredTransforms =
			new HashMap<>();

	public final static LineTransform split( final String separator ) {
		return new Split( separator );
	}
	
	public final static LineTransform columns( final int... columns ) {
		return new ColumnsSelector( columns );
	}

	public final static LineTransform replace(
			final int idColumn,
			final int targetColumn,
			final int dictionaryColumn,
			final Dictionary dictionary
	) {
		return new DictionaryReplacer(
				idColumn,
				targetColumn,
				dictionaryColumn,
				dictionary
		);
	}

	public final static LineTransform basename( final int column ) {
		return new Basename( column );
	}

	public final static LineTransform template(
			final int targetColumn,
			final String template,
			final int[] paramColumns
	) {
		return new TemplateReplacer( targetColumn, template, paramColumns );
	}

	public final static LineTransform template(
			final int targetColumn,
			final String template,
			final List<TemplateParameter> parameters
	) {
		return new TemplateReplacer( targetColumn, template, parameters );
	}

	public final static LineTransform lastMatchingFile(
			final int targetColumn,
			final int sourceColumn
	) {
		return new LastMatchingFileReplacer( targetColumn, sourceColumn );
	}

	public final static LineTransform replaceChar( final char c ) {
		return new CharReplacer( c, null );
	}

	public final static LineTransform replaceChar( final char c, final char r ) {
		return new CharReplacer( c, r );
	}

	public final static LineTransform replace( final String source ) {
		return new StringReplacer( source, "" );
	}

	public final static LineTransform replace(
			final String source,
			final String replacement
	) {
		return new StringReplacer( source, replacement );
	}

	public final static LineTransform toLowerCase( final int targetColumn ) {
		return new ToLowerCase( targetColumn );
	}

	public final static void registerImportedTransform(
			final String name,
			final ImportedTransform transform
	) {
		if ( !VariablesRegistry.registered( name ) ) {
			VariablesRegistry.registerTransform( name );
			fRegisteredTransforms.put( name, transform );
		}
		else {
			throw new RuntimeException(
					"Variable " + name + " is already registered"
			);
		}
	}

	public final static ImportedTransform importedTransform( final String name ) {
		if ( fRegisteredTransforms.containsKey( name ) ) {
			return fRegisteredTransforms.get( name );
		}
		else {
			throw new RuntimeException(
					"Transform " + name + " is not registered"
			);
		}
	}

	public final static LineTransform merge( final String separator ) {
		return new Merge( separator );
	}

	public final static LineTransform if_(
			final Predicate<String[]> predicate,
			final Branch mainBranch,
			final Branch alternativeBranch
	) {
		return new IfTransform( predicate, mainBranch, alternativeBranch );
	}

	public final static LineTransform writeToLog(
			final int logLevel,
			final String template,
			final List<TemplateParameter> templateParameters
	) {
		return new WriteToLog( logLevel, template, templateParameters );
	}

	public final static LineTransform insertColumns(
			final int position,
			final int count
	) {
		return new InsertColumns( position, count );
	}

}
