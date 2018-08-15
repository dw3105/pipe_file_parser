SET PATH=C:\Program Files\Java\jdk1.8.0_172\bin;%PATH%
mkdir blog\javamagic\pfp\antlr
copy ..\bin\main\blog\javamagic\pfp\antlr\*.class blog\javamagic\pfp\antlr
call antlr4.bat PFPSyntax.g4
call javac.exe PFPSyntax*.java
call antlr4.bat PFPSyntax.g4 -package blog.javamagic.pfp.antlr.generated
rem call run_tests.bat
