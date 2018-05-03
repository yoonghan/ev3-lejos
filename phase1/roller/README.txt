# Running from eclipse
1. Setup
sbt clean eclipse
2. Open project in Eclipse via File -> Import -> Existing Project into Workspace
3. Then right-click do a setting leJOSEV3 -> Convert to Lejos EV3 project
4. Right-click on project -> Properties and check Scala Compiler to make sure it points to 2.11.1


# SSH Changes
1. Vi to .ssh/ and create config file
Ciphers aes128-cbc

2. SSH to Lejos
ssh -oKexAlgorithms=+diffie-hellman-group1-sha1 root@192.168.1.48

3. Change the file of jrun
By appending /home/lejos/lib/*:
classpath=${LEJOS_HOME}/lib/ev3classes.jar:${LEJOS_HOME}/lib/dbusjava.jar:${LEJOS_HOME}/lib/opencv-2411.jar:${LEJOS_HOME}/libjna/usr/share/java/jna.jar:/home/lejos/lib/*:.

4. Scp scala-library.jar and scala-reflect.jar into /home/lejos/lib folder

# Run
1. Find src/main/java/*/MainApplication.java, right-click on the file and select Run->Lejos EV3 Program