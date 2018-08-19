package blog.javamagic.pfp.source;

import blog.javamagic.pfp.parser.PipeFileParser;

public final class Sources {

	public final static Source fromFile( final String filename ) {
		return new FileSource( filename );
	}

	public final static Source fromPipe( final PipeFileParser parser ) {
		return new PipeSource( parser );
	}

	public final static Source concatenate( final String... fileMasks ) {
		return new FileConcatenator( fileMasks );
	}

	public final static Source concatenate( final PipeFileParser parser ) {
		return new FileConcatenator( parser );
	}

	public final static Source filenames( final String... fileMasks ) {
		return new FilenamesSource( fileMasks );
	}

	public final static Source stdin() {
		return new StdinSource();
	}

	public final static Source head(
			final PipeFileParser parser,
			final int linesCount
	) {
		return new HeadSource( parser, linesCount );
	}

	public final static Source tail(
			final PipeFileParser parser,
			final int linesCount
	) {
		return new TailSource( parser, linesCount );
	}

}
