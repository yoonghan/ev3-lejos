# Setup
1. Download Scala ide, http://scala-ide.org/download/sdk.html
2. From Eclipse, Help -> Install New Software
3. Choose "Scala IDE composite update site" from dropdown menu
4. Install "ScalaTest for Scala IDE"
5. Restart eclipse
6. Change eclipse compilation to Scala 2.11, else the test case will not work.
7. Right-click on project and select properties
8. Click Build Path
9. Click on Project tab
10. Select roller project, make sure it's correctly identified.
11. Click on Order and Export tab, make sure roller project is below roller-test but top of everything.

# Run
1. Execute RoverRollerSpec - to test rover movement.
2. Execute UtilSpec - to test utility.
3. Execute TemporaryServer - this is a random generator so ACTUAL roller will move.