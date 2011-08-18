setlocal
set ANT_HOME=D:\tools\ant\apache-ant-1.8.2
set JAVA_HOME=C:\jdk1.6.0

set path=%JAVA_HOME%\bin;%ANT_HOME%\bin;%path%

ant %*

endlocal
