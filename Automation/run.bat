set projectLocation=C:\Users\arjit.yadav\Desktop\GIT NEOM\AutomationNEOM\Automation

cd %projectLocation%

set classpath=%projectLocation%\bin;%projectLocation%\lib\*

java org.testng.TestNG %projectLocation%\src\test\java\createRDIF.xml

pause