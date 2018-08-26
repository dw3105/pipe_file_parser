package blog.javamagic.pfp.parser;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import blog.javamagic.pfp.antlr.VarDefinitions;

public interface PipeFileParser {

	public PipeFileParser transform( Function<String[], String[]> transform );
	public PipeFileParser exclude( Predicate<String[]> predicate );
	public PipeFileParser include( Predicate<String[]> predicate );
	public PipeFileParser varDefinitions( VarDefinitions varDefs );
	public PipeFileParser unique();
	
	public PipeFileParser pipe();
	
	public void parse( Consumer<String[]> consumer );
	
	public void toFile( String outputFile, String separator );
	public void toFile( String outputFile );
	
	public void appendFile( String outputFile );
	public void appendFile( String outputFile, String separator );
	
	public void output( String separator );
	public void output();
	
	public void countLines();
	
	public void stop();
	
}
