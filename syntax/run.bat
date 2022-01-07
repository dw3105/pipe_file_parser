mkdir blog\javamagic\pfp\antlr
copy ..\bin\main\blog\javamagic\pfp\antlr\*.class blog\javamagic\pfp\antlr
call antlr4.bat PFPSyntaxLexer.g4
call antlr4.bat PFPSyntaxParser.g4
call javac.exe PFPSyntax*.java
call antlr4.bat PFPSyntaxLexer.g4 -package blog.javamagic.pfp.antlr.generated
call antlr4.bat PFPSyntaxParser.g4 -package blog.javamagic.pfp.antlr.generated
call run_tests.bat
