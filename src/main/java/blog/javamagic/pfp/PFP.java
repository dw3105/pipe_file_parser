package blog.javamagic.pfp;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.filter.LineFilters;
import blog.javamagic.pfp.filter.LineFilter;
import blog.javamagic.pfp.logger.LogOutput;
import blog.javamagic.pfp.logger.Logger;
import blog.javamagic.pfp.parser.Parsers;
import blog.javamagic.pfp.parser.PipeFileParser;
import blog.javamagic.pfp.source.Sources;
import blog.javamagic.pfp.transform.LineTransform;
import blog.javamagic.pfp.transform.LineTransforms;

public final class PFP {

	public static final int LOG_LEVEL_ALL = 5;
	public static final int LOG_LEVEL_DEBUG = 4;
	public static final int LOG_LEVEL_INFO = 3;
	public static final int LOG_LEVEL_WARNING = 2;
	public static final int LOG_LEVEL_ERROR = 1;
	public static final int LOG_LEVEL_SILENT = 0;
	
	private static int fLogLevel = LOG_LEVEL_WARNING;
	private static LogOutput fLogOutput = Logger.stdout();

	public final static PipeFileParser fromFile( final String filename ) {
		return Parsers.create( Sources.fromFile( filename ) );
	}
	
	public final static PipeFileParser concatenate(
			final String... fileMasks
	) {
		return Parsers.create( Sources.concatenate( fileMasks ) );
	}

	public final static PipeFileParser concatenate(
			final PipeFileParser parser
	) {
		return Parsers.create( Sources.concatenate( parser ) );
	}

	public final static PipeFileParser fromFilenames(
			final String... fileMasks
	) {
		return Parsers.create( Sources.filenames( fileMasks ) );
	}
	
	public final static LineTransform split(
			final String separator
	) {
		return LineTransforms.split( separator );
	}
	
	public final static LineTransform columns( final int... columns ) {
		return LineTransforms.columns( columns );
	}
	
	public final static LineTransform replace(
			final int targetColumn,
			final Dictionary dictionary,
			final int dictionaryColumn
	) {
		return replace(
				targetColumn,
				targetColumn,
				dictionary,
				dictionaryColumn
		);
	}

	public final static LineTransform replace(
			final int idColumn,
			final int targetColumn,
			final Dictionary dictionary,
			final int dictionaryColumn
	) {
		return LineTransforms.replace(
				idColumn,
				targetColumn,
				dictionaryColumn,
				dictionary
		);
	}
	
	public final static Dictionary dictionary( final PipeFileParser parser ) {
		return dictionary( parser, 0 );
	}

	public final static Dictionary dictionary(
			final PipeFileParser parser,
			final int idColumn
	) {
		return Dictionaries.create( parser, idColumn );
	}

	public final static void setLogLevel( final int logLevel ) {
		fLogLevel = logLevel;
	}

	public final static boolean logEnabled( final int minLogLevel ) {
		return ( fLogLevel >= minLogLevel );
	}

	public final static LineFilter contains( String substring ) {
		return LineFilters.contains( substring );
	}

	public final static LineTransform basename( final int column ) {
		return LineTransforms.basename( column );
	}

	public final static LineTransform basename() {
		return LineTransforms.basename( 0 );
	}

	public final static LineTransform template(
			final int targetColumn,
			final String template,
			final int... paramColumns
	) {
		return LineTransforms.template( targetColumn, template, paramColumns );
	}

	public final static LineTransform lastMatchingFile(
			final int targetColumn,
			final int sourceColumn
	) {
		return LineTransforms.lastMatchingFile( targetColumn, sourceColumn );
	}

	public final static LineTransform replaceChar( final char c ) {
		return LineTransforms.replaceChar( c );
	}

	public final static LineTransform replaceChar( final char c, final char r ) {
		return LineTransforms.replaceChar( c, r );
	}
	
	public final static String[] toLowerCase( final String[] line ) {
		final String[] new_line = new String[line.length];
		for ( int i = 0; i < line.length; ++i ) {
			new_line[i] = line[i].toLowerCase();
		}
		return new_line;
	}

	public final static LineFilter inDictionary(
			final int column,
			final Dictionary dict,
			final int dictColumn
	) {
		return inDictionary( column, column, dict, dictColumn, false );
	}

	public final static LineFilter inDictionary(
			final int column,
			final Dictionary dict,
			final int dictColumn,
			final boolean ignoreCase
	) {
		return inDictionary( column, column, dict, dictColumn, ignoreCase );
	}

	private final static LineFilter inDictionary(
			final int column,
			final int indexColumn,
			final Dictionary dict,
			final int dictColumn,
			final boolean ignoreCase
	) {
		return LineFilters.inDictionary(
				column,
				indexColumn,
				dict,
				dictColumn,
				ignoreCase
				);
	}

	public final static boolean checkArgumentsCount(
			final String[] args,
			final int expectedArgsCount
	) {
		if ( args.length == expectedArgsCount ) {
			return true;
		}
		else {
			Logger.log(
					LOG_LEVEL_WARNING,
					() -> "Invalid command line arguments count - %1$d, expected - %2$d",
					() -> new Object[] { args.length, expectedArgsCount }
			);
			return false;
		}
	}

	public final static void setLogOutput( final String filename ) {
		fLogOutput = Logger.file( filename );
	}

	public final static LogOutput logOutput() {
		return fLogOutput;
	}

	public static void setStdoutLogOutput() {
		fLogOutput = Logger.stdout();
	}

	public static void closeLog() {
		try {
			fLogOutput.close();
		}
		catch ( Throwable e ) {
			// ignore exceptions
		}
	}

	public static void setStderrLogOutput() {
		fLogOutput = Logger.stderr();
	}

	public final static void resolveStdoutLoggingConflict() {
		if ( fLogOutput.usesStdout() ) {
			setStderrLogOutput();
			setLogLevel( PFP.LOG_LEVEL_ERROR );
		}
	}

}
