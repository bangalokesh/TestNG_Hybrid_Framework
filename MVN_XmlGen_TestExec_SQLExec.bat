cd C:\Java_Programs - Copy\TestNG_Hybrid_Framework
call mvn -X exec:java -Dexec.mainClass="com.deloitte.testng_hybrid_framework.XmlGenerator" -Dexec.classpathScope="test"
call mvn test
call mvn -X exec:java -Dexec.mainClass="com.deloitte.testng_hybrid_framework.util.dbConnect2" -Dexec.cleanupDaemonThreads=false