package blog.javamagic.pfp.antlr;

import java.util.ArrayList;
import java.util.List;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.source.Source;
import blog.javamagic.pfp.source.Sources;

public final class Input extends AbstractContainer {

	public enum Type {
		file,
		files,
		fileNames,
		stdin,
		parser,
		head,
		tail,
		dictionary
	}

	private Type fType;
	private String fFilename;
	private final List<String> fMasks;
	private Parser fParser;
	private int fLinesCount = 10;
	private String fVariable;
	
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
		case dictionary:
			source = Dictionaries.get( fVariable );
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

}
