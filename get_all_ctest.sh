CUR_DIR=$(pwd)
echo $CUR_DIR
(for f in $(find . -name "*Test*.java" | cut -d'/' -f1-2 | sort -u); do cd $CUR_DIR/$f/ && JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64" mvn confuzz:identify > identify_log; done)