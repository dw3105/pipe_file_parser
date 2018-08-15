package blog.javamagic.pfp.filter;

import blog.javamagic.pfp.dictionary.Dictionary;

final class InDictionary implements LineFilter {

	private final int fColumn;
	private final int fIndexColumn;
	private final Dictionary fDictionary;
	private final int fDictColumn;
	private final boolean fIgnoreCase;

	public InDictionary(
			final int column,
			final int indexColumn,
			final Dictionary dict,
			final int dictColumn,
			final boolean ignoreCase
	) {
		fColumn = column;
		fIndexColumn = indexColumn;
		fDictionary = dict;
		fDictColumn = dictColumn;
		fIgnoreCase = ignoreCase;
	}

	@Override
	public final boolean f( final String[] line ) {
		final String id = line[fIndexColumn];
		final String value = line[fColumn];
		final String[] dict_line = fDictionary.line( id );
		if ( dict_line != null ) {
			final String dict_value = dict_line[fDictColumn];
			return (
					fIgnoreCase
					? value.equalsIgnoreCase( dict_value )
							: value.equals( dict_value )
			);
		}
		return false;
	}

}
