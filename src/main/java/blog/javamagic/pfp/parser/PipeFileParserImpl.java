package blog.javamagic.pfp.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import blog.javamagic.pfp.PFP;
import blog.javamagic.pfp.antlr.VarDefinitions;
import blog.javamagic.pfp.logger.Logger;
import blog.javamagic.pfp.source.Source;
import blog.javamagic.pfp.source.Sources;

final class PipeFileParserImpl implements PipeFileParser {

	private enum ItemType {
		transform,
		includeFilter,
		excludeFilter,
		varDefinitions
	}

	private final static class QueueItem {
		private final ItemType itemType;
		private final int num;

		public QueueItem( final ItemType itemType, final int num ) {
			this.itemType = itemType;
			this.num = num;
		}
	}

	private final static class StringArrayKey {
		private final String[] fLine;
		private final int fHashCode;

		public StringArrayKey( final String[] line ) {
			fLine = line;
			fHashCode = Arrays.hashCode( fLine );
		}

		@Override
		public final int hashCode() {
			return fHashCode;
		}

		@Override
		public final boolean equals( final Object obj ) {
			if ( this == obj ) return true;
			if ( obj == null ) return false;
			if ( getClass() != obj.getClass() ) return false;
			StringArrayKey other = (StringArrayKey) obj;
			if ( !Arrays.equals( fLine, other.fLine ) ) {
				return false;
			}
			return true;
		}
	}

	private final Source fSource;
	private final List<Function<String[], String[]>> fTransforms;
	private final List<Predicate<String[]>> fFilters;
	private final List<VarDefinitions> fVarDefinitions;
	private final List<QueueItem> fQueue;
	private final Map<StringArrayKey, Boolean> fUniqueLines;
	
	private boolean fUnique = false;
	private boolean fStopped = false;

	public PipeFileParserImpl( final Source source ) {
		fSource = source;
		fTransforms = new ArrayList<>();
		fFilters = new ArrayList<>();
		fVarDefinitions = new ArrayList<>();
		fQueue = new ArrayList<>();
		fUniqueLines = new HashMap<>();
	}

	@Override
	public final PipeFileParser transform(
			final Function<String[], String[]> transform
	) {
		final int num = fTransforms.size();
		fTransforms.add( transform );
		fQueue.add( new QueueItem( ItemType.transform, num ) );
		return this;
	}

	@Override
	public final PipeFileParser include( final Predicate<String[]> predicate ) {
		final int num = fFilters.size();
		fFilters.add( predicate );
		fQueue.add( new QueueItem( ItemType.includeFilter, num ) );
		return this;
	}

	@Override
	public final PipeFileParser exclude( final Predicate<String[]> predicate ) {
		final int num = fFilters.size();
		fFilters.add( predicate );
		fQueue.add( new QueueItem( ItemType.excludeFilter, num ) );
		return this;
	}

	@Override
	public final PipeFileParser varDefinitions( final VarDefinitions varDefs ) {
		final int num = fVarDefinitions.size();
		fVarDefinitions.add( varDefs );
		fQueue.add( new QueueItem( ItemType.varDefinitions, num ) );
		return this;
	}

	@Override
	public final PipeFileParser unique() {
		fUnique = true;
		return this;
	}

	@Override
	public final PipeFileParser pipe() {
		return Parsers.create( Sources.fromPipe( this ) );
	}

	@Override
	public final void parse( final Consumer<String[]> consumer ) {
		final AtomicInteger lines_parsed = new AtomicInteger( 0 );
		fSource.forEachLine(
				( line ) -> {
					if ( fStopped ) {
						fSource.stop();
					}
					else {
						Logger.log(
								PFP.LOG_LEVEL_ALL,
								() -> "Processed line: %1$s",
								() -> new Object[] { Arrays.toString( line ) }
						);
						if ( processLine( line, consumer ) ) {
							lines_parsed.incrementAndGet();
						}
					}
				} );
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Processed lines: %1$d",
				() -> new Object[] { lines_parsed.get() } );
	}
	
	private final boolean processLine(
			final String[] line,
			final Consumer<String[]> consumer
	) {
		try {
			PFP.setCurrentLine( line );
			String[] processed_line = line;
			for ( final QueueItem item : fQueue ) {
				switch ( item.itemType ) {
				case includeFilter:
				case excludeFilter:
					try {
						Logger.log(
								PFP.LOG_LEVEL_ALL,
								() -> "Applying filter #%1$d",
								() -> new Object[] { ( item.num + 1 ) }
						);
						final boolean include =
								item.itemType == ItemType.includeFilter;
						final Predicate<String[]> predicate =
								fFilters.get( item.num );
						final boolean test_passed =
								predicate.test( processed_line );
						final boolean line_passed =
								( include && test_passed )
										|| ( ( !include ) && ( !test_passed ) );
						if ( !line_passed ) {
							final String[] current_line = processed_line;
							Logger.log(
									PFP.LOG_LEVEL_ALL,
									() -> "Filter #%1$d failed for line %2$s",
									() -> new Object[] {
											( item.num + 1 ),
											Arrays.toString( current_line )
									}
							);
							return false;
						} 
					}
					catch ( Throwable e ) {
						final String[] current_line = processed_line;
						Logger.log(
								PFP.LOG_LEVEL_ERROR,
								() -> "Exception (%3$s) caught while applying "
										+ "includeFilter #%1$d for line %2$s",
								() -> new Object[] {
										( item.num + 1 ),
										Arrays.toString( current_line ),
										e.getMessage()
								}
						);
						return false;
					}
					break;
				case transform:
					try {
						Logger.log(
								PFP.LOG_LEVEL_ALL,
								() -> "Applying transformation #%1$d",
								() -> new Object[] { ( item.num + 1 ) }
						);
						final Function<String[], String[]> function =
								fTransforms.get( item.num );
						final String[] before = processed_line;
						processed_line = function.apply( processed_line );
						final String[] after = processed_line;
						PFP.setCurrentLine( after );
						Logger.log(
								PFP.LOG_LEVEL_ALL,
								() -> "%1$s converted to %2$s",
								() -> new Object[] {
										Arrays.toString( before ),
										Arrays.toString( after )
								}
						);
					}
					catch ( Throwable e ) {
						final String[] current_line = processed_line;
						Logger.log(
								PFP.LOG_LEVEL_ERROR,
								() -> "Exception (%3$s) caught while applying "
										+ "transformation #%1$d for line %2$s",
								() -> new Object[] {
										( item.num + 1 ),
										Arrays.toString( current_line ),
										e.getMessage()
								}
						);
						return false;
					}
					break;
				case varDefinitions:
					try {
						final VarDefinitions var_defs =
								fVarDefinitions.get( item.num );
						var_defs.defineVars();
					}
					catch ( Throwable e ) {
						final String[] current_line = processed_line;
						Logger.log(
								PFP.LOG_LEVEL_ERROR,
								() -> "Exception (%3$s) caught in defining "
										+ "variables block #%1$d for line %2$s",
								() -> new Object[] {
										( item.num + 1 ),
										Arrays.toString( current_line ),
										e.getMessage()
								}
						);
						return false;
					}
					break;
				default:
					throw new Error(
							"Invalid item type: '" + item.itemType + "'"
					);
				}
			}
			
			if ( fUnique ) {
				if ( !isUnique( processed_line ) ) {
					final String[] current_line = processed_line;
					Logger.log(
							PFP.LOG_LEVEL_ALL,
							() -> "Line %1$s is not unique",
							() -> new Object[] { 
									Arrays.toString( current_line )
							}
					);
					return false;
				}
			}
			consumer.accept( processed_line );
			return true;
		}
		catch ( Throwable e ) {
			throw new RuntimeException(
					"Unexpected exception caught while parsing: "
							+ e.getMessage(),
					e
			);
		}
	}

	private final boolean isUnique( final String[] line ) {
		final StringArrayKey key = new StringArrayKey( line );
		if ( !fUniqueLines.containsKey( key ) ) {
			fUniqueLines.put( key, Boolean.valueOf( true ) );
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public final void toFile(
			final String outputFile,
			final String separator
	) {
		toFile( outputFile, separator, false );
	}

	private final void toFile(
			final String outputFile,
			final String separator,
			final boolean append
	) {
		Logger.log(
				PFP.LOG_LEVEL_INFO,
				() -> "Writing output to %1$s",
				() -> new Object[] { outputFile }
		);
		try (
				final Writer writer =
						new BufferedWriter(
								new FileWriter( new File( outputFile ), append )
						)
		) {
			final String sep = ( separator != null ? separator : "" );
			parse( ( line ) -> writeLine( writer, line, sep ) );
		}
		catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	@Override
	public final void toFile( final String outputFile ) {
		toFile( outputFile, "" );
	}

	@Override
	public final void appendFile( final String outputFile ) {
		appendFile( outputFile, "" );
	}

	@Override
	public final void appendFile(
			final String outputFile,
			final String separator
	) {
		toFile( outputFile, separator, true );
	}

	private final static void writeLine(
			final Writer writer,
			final String[] line,
			final String separator
	) {
		try {
			writer.write( lineToString( line, separator ) );
			writer.write( System.lineSeparator() );
		}
		catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	private final static String lineToString(
			final String[] line,
			final String separator
	) {
		final StringBuilder sb = new StringBuilder( line[0] );
		for ( int i = 1; i < line.length; ++i ) {
			sb.append( separator ).append( line[i] );
		}
		return sb.toString();
	}

	@Override
	public final void countLines() {
		final AtomicInteger counter = new AtomicInteger( 0 );
		parse( ( line ) -> counter.incrementAndGet() );
		System.out.println( counter.get() );
	}

	@Override
	public final void output( final String separator ) {
		parse(
				( line ) -> {
					final int len = line.length;
					if ( len > 0 ) {
						final StringBuilder sb = new StringBuilder();
						for ( int i = 0; i < len; ++i ) {
							if ( i > 0 ) {
								sb.append( separator );
							}
							sb.append( line[i] );
						}
						System.out.println( sb.toString() );
					}
				}
		);
	}

	@Override
	public final void output() {
		output( "" );
	}

	@Override
	public final void stop() {
		fStopped = true;
	}

}
