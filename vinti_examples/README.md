sbt
sbt package
spark-submit --class "SimpleApp" --master local target/scala-2.10/simple-project_2.10-1.0.jar

Note: Directory structure should be:
╰─$ find .
.
./simple.sbt
./src
./src/main
./src/main/scala
./src/main/scala/SimpleApp.scala