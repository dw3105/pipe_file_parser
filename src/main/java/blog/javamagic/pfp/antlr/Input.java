package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.source.Source;
import blog.javamagic.pfp.source.Sources;

public final class Input extends AbstractContainer {

	public enum Type {
		csv,
		file,
		files,
		fileNames,
		stdin,
		parser,
		head,
		tail,
		dictionary,
		countLines,
		dictLookup
	}

	private Type fType;
	private String fFilename;
	private final List<String> fMasks;
	private Parser fParser;
	private int fLinesCount = 10;
	private String fVariable;
	private String fDictionaryName;
	private String fLookupString;
	private String fLookupVariable;
	private int fLookupColumn;
	
	public Input() {
		fMasks = new ArrayList<>();
	}

	public final void setType( final Type type ) {
		fType = type;
	}

	public final void setFilename( final String filename ) {
		fFilename = PFPSyntax.filename( filename );
	}

	public final void addFilemask( final String mask ) {
		fMasks.add( PFPSyntax.filename( mask ) );
	}

	public final void setParser( final Parser parser ) {
		fParser = parser;
	}

	public final Source source() {
		final Source source;
		switch ( fType ) {
		case csv:
			source = Sources.fromCsv( fFilename );
			break;
		case file:
			source = Sources.fromFile( fFilename );
			break;
		case fileNames:
			source = Sources.filenames( fileMasks() );
			break;
		case files:
			if ( fParser != null ) {
				source = Sources.concatenate( fParser.pipeFileParser() );
			}
			else {
				source = Sources.concatenate( fileMasks() );
			}
			break;
		case parser:
			source = Sources.fromPipe( fParser.pipeFileParser() );
			break;
		case stdin:
			source = Sources.stdin();
			break;
		case head:
			source = Sources.head( fParser.pipeFileParser(), fLinesCount );
			break;
		case tail:
			source = Sources.tail( fParser.pipeFileParser(), fLinesCount );
			break;
		case countLines:
			source = Sources.countLines( fParser.pipeFileParser() );
			break;
		case dictionary:
			source = Dictionaries.get( fVariable );
			break;
		case dictLookup:
			source =
					Sources.dictLookup(
							fDictionaryName,
							fLookupString,
							fLookupVariable,
							fLookupColumn
					);
			break;
		default:
			throw new Error( "Invalid type - " + fType );
		}
		return source;
	}

	private final String[] fileMasks() {
		return fMasks.toArray( new String[fMasks.size()] );
	}

	public final void setLinesCount( final int linesCount ) {
		fLinesCount = linesCount;
	}

	public final void setVariable( final String variable ) {
		fVariable = variable;
	}

	public final void setDictionaryName( final String dictionaryName ) {
		fDictionaryName = dictionaryName;
	}

	public final void setLookupString( final String lookupString ) {
		fLookupString = lookupString;
	}

	public final void setLookupVariable( final String lookupVariable ) {
		fLookupVariable = lookupVariable;
	}

	public final void setLookupColumn( final int lookupColumn ) {
		fLookupColumn = lookupColumn;
	}

}
