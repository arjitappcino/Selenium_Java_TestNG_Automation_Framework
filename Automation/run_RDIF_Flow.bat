cd C:\Users\arjit.yadav\Desktop\NeomGIT\AutomationNEOM\Automation
mvn test -Denv.USER="%ProjectName%" -Denv.PATH="src\test\java\RDIF_flow.xml" -Denv.REGION="%Region%" -Denv.Environment="%Environment%" -Denv.CAT="%Category%"