package blog.javamagic.pfp.transform;

import java.util.Arrays;

import blog.javamagic.pfp.dictionary.Dictionary;

final class DictionaryReplacer implements LineTransform {

	private final int fIdColumn;
	private final int fTargetColumn;
	private final int fDictionaryColumn;
	private final Dictionary fDictionary;

	public DictionaryReplacer(
			final int idColumn,
			final int targetColumn,
			final int dictionaryColumn,
			final Dictionary dictionary
	) {
		fIdColumn = idColumn;
		fTargetColumn = targetColumn;
		fDictionaryColumn = dictionaryColumn;
		fDictionary = dictionary;
	}

	@Override
	public final String[] t( final String[] line ) {
		final String[] new_line = Arrays.copyOf( line, line.length );
		final String id = line[fIdColumn];
		final String[] dict_line = fDictionary.line( id );
		new_line[fTargetColumn] = dict_line[fDictionaryColumn];
		return new_line;
	}

}
