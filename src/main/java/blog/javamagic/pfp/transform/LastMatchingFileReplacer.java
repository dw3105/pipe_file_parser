package blog.javamagic.pfp.transform;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import blog.javamagic.pfp.file.WildcardMatcher;

final class LastMatchingFileReplacer implements LineTransform {

	private final int fTargetColumn;
	private final int fSourceColumn;

	public LastMatchingFileReplacer( final int targetColumn, final int sourceColumn ) {
		fTargetColumn = targetColumn;
		fSourceColumn = sourceColumn;
	}

	@Override
	public final String[] t( final String[] line ) {
		final String file_mask = line[fSourceColumn];
		final List<File> files = new ArrayList<>();
		WildcardMatcher.match( file_mask, files::add );
		final int size = files.size();
		final File last_file;
		if ( size > 0 ) {
			last_file = files.get( size - 1 );
		}
		else {
			last_file = null;
		}
		final String[] new_line = Arrays.copyOf( line, line.length );
		new_line[fTargetColumn] =  ( last_file != null ? last_file.getName() : "" );
		return new_line;
	}

}
