#! /bin/sh

jbin=${JAVA_HOME:-}${JAVA_HOME:+/bin/}
java=${jbin}java
javac=${jbin}javac

jversion=` $javac -version 2>&1 | head -1 `
case $jversion in
*1.6*|*1.7*) true;;
*) >&2 echo "Warning: Bad javac version: $jversion";;
esac

rm -rf bootcp
mkdir bootcp

$javac -d bootcp *.java

mv bootcp/*.class .

rm -f DVMTest.zip
zip DVMTest.zip *.java *.class `find bootcp -name '*.class'` MAKE.sh

exit 0

)))) Sample output:

Note: AnonymousClassLoader.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
  adding: AnonymousClassLoader.java (deflated 72%)
  adding: CPParse.java (deflated 73%)
  adding: DVMTest.java (deflated 73%)
  adding: Unsafe.java (deflated 78%)
  adding: CPParser.class (deflated 43%)
  adding: DVMTest$Inter.class (deflated 17%)
  adding: DVMTest$Temp.class (deflated 42%)
  adding: DVMTest$TempBase.class (deflated 30%)
  adding: DVMTest.class (deflated 44%)
  adding: bootcp/java/dyn/AnonymousClassLoader$Constant.class (deflated 55%)
  adding: bootcp/java/dyn/AnonymousClassLoader$CPParser.class (deflated 43%)
  adding: bootcp/java/dyn/AnonymousClassLoader.class (deflated 50%)
  adding: bootcp/sun/misc/Unsafe.class (deflated 62%)
  adding: MAKE.sh (deflated 61%)
