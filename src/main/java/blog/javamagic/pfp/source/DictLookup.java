package blog.javamagic.pfp.source;

import java.util.function.Consumer;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.dictionary.Dictionary;
import blog.javamagic.pfp.variable.Variables;

final class DictLookup extends AbstractSource {

	private final String fDictionaryName;
	private final String fLookupString;
	private final String fLookupVariable;
	private final int fLookupColumn;

	public DictLookup(
			final String dictionaryName,
			final String lookupString,
			final String lookupVariable,
			final int lookupColumn
	) {
		fDictionaryName = dictionaryName;
		fLookupString = lookupString;
		fLookupVariable = lookupVariable;
		fLookupColumn = lookupColumn;
	}

	@Override
	public final void forEachLine( final Consumer<String[]> consumer ) {
		final Dictionary dict = Dictionaries.get( fDictionaryName );
		final String id;
		if ( fLookupString != null ) {
			id = fLookupString;
		}
		else {
			id = Variables.getString( fLookupVariable );
		}
		final String[] line = dict.line( id );
		if ( line != null ) {
			final String value = line[fLookupColumn];
			consumer.accept( new String[] { value } );
		}
	}

}
