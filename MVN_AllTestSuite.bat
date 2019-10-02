cd C:\Java_Programs - Copy\TestNG_Hybrid_Framework
call mvn exec:java -Dexec.mainClass="com.deloitte.testng_hybrid_framework.SetupTestCasesAndSuites" -Dexec.classpathScope="test"
call mvn exec:java -Dexec.mainClass="com.deloitte.testng_hybrid_framework.ExecuteAllTests" -Dexec.classpathScope="test"
call mvn exec:java -Dexec.mainClass="com.deloitte.testng_hybrid_framework.util.dbConnect2" -Dexec.cleanupDaemonThreads=false