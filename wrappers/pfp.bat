@echo off
set JAVA_1_8_HOME="C:\Program Files\Java\jre1.8.0_172\bin"
set PFP_HOME="D:\!work\pfp"

%JAVA_1_8_HOME%\java.exe -cp "%PFP_HOME%;%PFP_HOME%\pipe_file_parser.jar" blog.javamagic.pfp.PfpRunner %*