package blog.javamagic.pfp.filter;

import java.util.concurrent.atomic.AtomicBoolean;

import blog.javamagic.pfp.dictionary.Dictionaries;
import blog.javamagic.pfp.dictionary.Dictionary;

final class Lookup implements LineFilter {

	private final Dictionary fDictionary;
	private final int fColumn;

	public Lookup( final String dictionary, final int column ) {
		fDictionary = Dictionaries.get( dictionary );
		fColumn = ( column != -1 ? column : 0 );
	}

	@Override
	public final boolean f( final String[] line ) {
		final AtomicBoolean found = new AtomicBoolean( false );
		fDictionary.forEachLine(
				( dict_line ) -> {
					final String dict_str = dict_line[fColumn];
					for ( final String str : line ) {
						if ( str.contains( dict_str ) ) {
							fDictionary.stop();
							found.set( true );
						}
					}
				}
		);
		return found.get();
	}

}
