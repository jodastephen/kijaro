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
java -jar %COMPILER_JAR% %COMPILER_FLAGS% SimpleString.java && java SimpleString

ECHO ChangeType.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% ChangeType.java && java ChangeType

ECHO FreeStaticMember.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeStaticMember.java && java FreeStaticMember

ECHO FreeStaticMethod.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeStaticMethod.java && java FreeStaticMethod

ECHO NestedInForeach.java
java -jar %COMPILER_JAR% %COMPILER_FLAGS% NestedInForeach.java && java NestedInForeach

ECHO AnonClass.java (will fail to compile)
java -jar %COMPILER_JAR% %COMPILER_FLAGS% AnonClass.java && java AnonClass

ECHO ArrayInput.java (will fail to compile)
java -jar %COMPILER_JAR% %COMPILER_FLAGS% ArrayInput.java && java ArrayInput

ECHO FreeLocal.java (will fail to compile)
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeLocal.java && java FreeLocal

ECHO FreeMember.java (will fail to compile)
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeMember.java && java FreeMember

ECHO FreeMethod.java (will fail to compile)
java -jar %COMPILER_JAR% %COMPILER_FLAGS% FreeMethod.java && java FreeMethod
