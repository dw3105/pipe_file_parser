package blog.javamagic.pfp.transform;

final class StringReplacer implements LineTransform {

	private final String fSource;
	private final String fReplacement;

	public StringReplacer( final String source, final String replacement ) {
		fSource = source;
		fReplacement = replacement;
	}

	@Override
	public final String[] t( final String[] line ) {
		final int len = line.length;
		final String[] new_line = new String[len];
		for ( int i = 0; i < len; ++i ) {
			new_line[i] = line[i].replaceAll( fSource, fReplacement );
		}
		return new_line;
	}

}
