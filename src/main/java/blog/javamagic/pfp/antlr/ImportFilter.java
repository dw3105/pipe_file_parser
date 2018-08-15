package blog.javamagic.pfp.antlr;

import java.lang.reflect.Constructor;

import blog.javamagic.pfp.filter.LineFilters;
import blog.javamagic.pfp.filter.ImportedFilter;

public final class ImportFilter extends AbstractContainer {

	private String fClassName;
	private String fName;

	public final void setClassName( final String className ) {
		fClassName = className;
	}

	public final void setName( final String name ) {
		fName = name;
	}

	@Override
	protected final void execute() {
		try {
			ClassLoader class_loader = this.getClass().getClassLoader();
			Class<?> loaded_class = class_loader.loadClass( fClassName );
			Constructor<?> constructor = loaded_class.getConstructor();
			ImportedFilter filter = (ImportedFilter) constructor.newInstance();
			final String name;
			if ( fName != null ) {
				name = fName;
			}
			else {
				name = loaded_class.getSimpleName();
			}
			LineFilters.registerImportedFilter( name, filter );
		}
		catch ( Throwable e ) {
			throw new RuntimeException(
					"Unable to load filter from class "
							+ fClassName
							+ ": "
							+ e.getMessage(),
					e
			);
		}
	}

}
