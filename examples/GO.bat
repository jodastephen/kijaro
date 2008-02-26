@ECHO OFF

SET COMPILER_JAR=..\langtools\dist\bootstrap\lib\javac.jar
SET COMPILER_FLAGS=-Xlint:unchecked -cp .

DEL *.class

REM Compile utility class.
ECHO Utils.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% Utils.java

ECHO SimpleInteger.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% SimpleInteger.java && java SimpleInteger

ECHO SimpleString.java
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% SimpleString.java && java SimpleString

ECHO ChangeType.java
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% ChangeType.java && java ChangeType

ECHO FreeStaticMember.java
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeStaticMember.java && java FreeStaticMember

ECHO FreeStaticMethod.java
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeStaticMethod.java && java FreeStaticMethod

ECHO NestedInForeach.java
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% NestedInForeach.java && java NestedInForeach

ECHO ArrayInput.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% ArrayInput.java && java ArrayInput

ECHO AnonClass.java (will fail to compile)
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% AnonClass.java && java AnonClass

ECHO FreeLocal.java (will fail to compile)
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeLocal.java && java FreeLocal

ECHO FreeMember.java (will fail to compile)
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeMember.java && java FreeMember

ECHO FreeMethod.java (will fail to compile)
REM java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeMethod.java && java FreeMethod
