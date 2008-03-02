@ECHO OFF

SET COMPILER_JAR=..\langtools\dist\bootstrap\lib\javac.jar
SET COMPILER_FLAGS=-Xlint:unchecked -cp .

DEL *.class

REM Compile utility class.
ECHO Utils.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% Utils.java
pause

ECHO SimpleInteger.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% SimpleInteger.java && java SimpleInteger
pause

ECHO SimpleNotStatic.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% SimpleNotStatic.java && java SimpleNotStatic
pause

ECHO SimpleString.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% SimpleString.java && java SimpleString
pause

ECHO ChangeType.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% ChangeType.java && java ChangeType
pause

ECHO FreeStaticMember.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeStaticMember.java && java FreeStaticMember
pause

ECHO FreeStaticMethod.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeStaticMethod.java && java FreeStaticMethod
pause

ECHO NestedInForeach.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% NestedInForeach.java && java NestedInForeach
pause

ECHO ArrayInput.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% ArrayInput.java && java ArrayInput
pause

ECHO FreeLocal.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeLocal.java && java FreeLocal
pause

ECHO FreeLocalNotStatic.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeLocalNotStatic.java && java FreeLocalNotStatic
pause

ECHO FreeMember.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeMember.java && java FreeMember
pause

ECHO FreeMethod.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeMethod.java && java FreeMethod
pause

ECHO AnonClass.java (will fail to compile)
java -jar %COMPILER_JAR% %COMPILER_FLAGS% AnonClass.java && java AnonClass
