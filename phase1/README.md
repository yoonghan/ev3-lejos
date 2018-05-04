# Phase one
This project on phase 1 is to prove on the following validity and functionality of Lejos.
* Lejos installation and usage
* Scala works
* Akka and concurrency for multiple sensors and motor.
* Test/Build project is seperated, this is due to the Ev3 Lejos plugin that uploads unnecessary jars into Ev3's brain unit.


## Computer Installation
1. Download Eclipse, if possible the release is Oxygen.3 Release (4.7.3).
2. Click *Help* -> *Install Software*.
3. ADD http://www.lejos.org/tools/eclipse/plugin/ev3/ , install all the packages.
4. ADD http://download.scala-ide.org/sdk/lithium/e47/scala212/stable/site , at minimal install package name that stars with "Scala IDE".
5. Download and install SBT 0.13.11.
6. Install Putty

## Lejos setup
1. Follow Lejos ev3 to install Lejos into the Ev3's brain.
2. Install ssh, for windows program install winscp. This is to publish file into Lejos.
3. Download scala-library.jar [https://mvnrepository.com/artifact/org.scala-lang/scala-library/2.11.11] and [https://mvnrepository.com/artifact/org.scala-lang/scala-reflect/2.11.11].
4. Try SSH to Ev3's brain
```
#For Unix, Macbook. For Windows you can putty but set the correct ciphers and algorithm.
mmpkl05$ echo 'Ciphers aes128-cbc' > ~/.ssh/config
mmpkl05$ ssh -oKexAlgorithms=+diffie-hellman-group1-sha1 root@10.0.1.1
root@lejoshan# cd /home/root/lejos/bin
root@lejoshan# vi jrun
```
5. Edit the file and add /home/lejos/lib/* at end of the classpath
```
classpath=${LEJOS_HOME}/lib/ev3classes.jar:${LEJOS_HOME}/lib/dbusjava.jar:${LEJOS_HOME}/lib/opencv-2411.jar:${LEJOS_HOME}/libjna/usr/share/java/jna.jar:/home/lejos/lib/*:.
```
6. Save then continue
```
root@lejoshan# exit
#For Unix, Macbook. For windows you can use Winscp
mmpkl05$ scp scala-library.jar root@10.0.1.1:/home/lejos/lib/scala-library.jar
mmpkl05$ scp scala-reflect.jar root@10.0.1.1:/home/lejos/lib/scala-reflect.jar
```
7. If ssh into the library again it'll look with the files
```
root@lejoshan:/home/lejos/lib# ls -lrt
...
-rw-r--r--    1 root     root      5748808 May  2 01:26 scala-library.jar
-rw-r--r--    1 root     root      4602981 May  2 01:26 scala-reflect.jar
...
```

## Validate Installation
1. Open console and run the command sbt
```
git_lego mmpkl05$ sbt about
[info] Set current project to git_lego (in build file:/)
[info] This is sbt 0.13.11
[info] The current project is {file:/}git_lego 0.1-SNAPSHOT
[info] The current project is built against Scala 2.10.6
[info] Available Plugins: sbt.plugins.IvyPlugin, sbt.plugins.JvmPlugin, sbt.plugins.CorePlugin, sbt.plugins.JUnitXmlReportPlugin
[info] sbt, sbt plugins, and build definitions are using Scala 2.10.6
```
## Preparing Ev3 hardware
1. Need 2 Large motors
2. Need a touch sensor
3. Wireless adapter, if not possible, need to find the IPAddress to connect to pc via USB.
![alt text][deviceImage]
[deviceImage]: https://github.com/yoonghan/ev3-lejos/tree/master/phase1/img/device.jpg "EV3 Setup"

## Begin run and testing
1. Open console/command line and go to phase1/roller project.
2. Run
```
cd phase1/roller
sbt clean eclipse
sbt publish
```
3. Run
```
cd ../../phase1/roller_test
sbt clean eclipse
```
4. Boot eclipse and import via File->Import->Existing Project into Workspace. Choose both project one after the other into eclipse workspace.
5. Right-click on each project, select Properties, choose "Scala Compiler", check use "Project Settings" and set "Scala Installation" as "2.11".
6. Right-click on roller project, and select Lejos EV3 Project -> Convert to Lejos EV3 project.
7. Right-click on roller-builder project, select Properties, choose "Java Build Path", click "Project" tab and add roller project. Click "Order and Export" tab, move roller to the Top and below roller-builder projects.

## Test installation
1. Expand roller-builder
2. Run com.walcron.lego.test.RoverRollerSpec and com.walcron.lego.test.UtilSpec, see if both are running fine. These are software tests.
3. Once both are ok, it's time for real server test.
4. Firstly, expand roller and change src/main/resources/config.properties file to the correct ip. NOTE: This is only for wireless, need to find ways to connect via USB.
5. Epand roller-builder and right-click on src/test/scala...TemporaryServer.java -> Run As -> Scala Application
```
TO STOP SERVER: Use Ctrl-C
May 04, 2018 7:54:26 AM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [0.0.0.0:8080]
May 04, 2018 7:54:26 AM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
->S
->A
->W
```
6. Expand roller project and right-click on src/main/java...MainApplication.java -> Run As -> LeJOS Ev3 program. It should display
```
Library on classpath: /Users/mmpkl05/.ivy2/cache/com.typesafe.akka/akka-actor_2.11/jars/akka-actor_2.11-2.3.8.jar
Library on classpath: /Users/mmpkl05/.ivy2/cache/com.typesafe/config/bundles/config-1.2.1.jar
Jar file has been created successfully
Using the EV3 menu for upload and to execute program
Uploading to 192.168.1.48 ...
Uploading nv-websocket-client-1.3 2.jar to 192.168.1.48 ...
Uploading lejos-ev3-api-0.9.1-beta.jar to 192.168.1.48 ...
Uploading dbus-java-2.7.jar to 192.168.1.48 ...
Uploading cx.ath.matthew-0.8.jar to 192.168.1.48 ...
Uploading jna-3.2.7.jar to 192.168.1.48 ...
Uploading akka-actor_2.11-2.3.8.jar to 192.168.1.48 ...
Uploading config-1.2.1.jar to 192.168.1.48 ...
Program has been uploaded
Running program ...
leJOS EV3 plugin launch complete
EV3Control has been started successfully
```
7. Wait. Yes wait 5 minute for it to load, problem is due to Akka threading setup.
8. Let it roll and move.

## Project test
1. Using websocket it waits for input W,A,S,D to control both motors. Both motors are synchronized.
2. If the touch sensor is pressed, all inputs are halt and finish, then it will reverse once.
3. Click on enter will exit Ev3.
