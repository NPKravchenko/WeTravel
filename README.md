# WeTravel

Environment

Make sure that you met the following preconditions:
- OS Windows 64
- Eclipse IDE is installed in your system
- The last JDK version is installed in your system
- The last Maven version is installed in your system
- Chrome browser is installed

Environment Variables
- Make sure JDK is installed, and JAVA_HOME environment variable is configured
- Make sure that Apache Maven is installed, MAVEN_HOME system variable is configured and %MAVEN_HOME%\bin added to PATH

To open project in Eclipse
- Launch Eclipse IDE
- Import project from the local folder
- Specify JRE (SE-13) via Project/Properties/Java Build Path
- Update the project (WeTravel context menu -> Maven -> Update Project...)
- Go to Run configuration (WeTravel context menu -> Run As -> Run Configurations) and specify suite as src\test\resources\TestSuite\PositiveTests.xml
- Run TestNG configuration
 
To run test:
- Go to the folder where project is located
- run command:  mvn clean test
    
    
