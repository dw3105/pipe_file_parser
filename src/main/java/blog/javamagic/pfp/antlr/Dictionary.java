package blog.javamagic.pfp.antlr;

import blog.javamagic.pfp.dictionary.Dictionaries;

public final class Dictionary extends AbstractContainer {

	private String fName;
	private Parser fParser;
	private int fIndexColumn = 0;

	public final void setName( final String name ) {
		fName = name;
	}

	public final void setParser( final Parser parser ) {
		fParser = parser;
	}

	@Override
	protected final void execute() {
		final blog.javamagic.pfp.dictionary.Dictionary dict =
				Dictionaries.create( fParser.pipeFileParser(), fIndexColumn );
		Dictionaries.registerDictionary( fName, dict );
	}

	public void setIndexColumn( final int indexColumn ) {
		fIndexColumn = indexColumn;
	}

}
