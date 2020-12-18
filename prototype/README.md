# Prototypes
To test lejos capabilities using bluetooth

## Prerequisite
1. Setup Lejos in Lego Brick
2. Install Eclipse with Lejos
3. Install Android Studio 

## Installation
1. From Eclipse import the project Java_DC with File->Import->Existing Project From Workspace->"Java_DC"
2. (Optional) From Android Studio open the project with File-Open->"Android_DC"

## Execution
### For Android_DC project it will display message sent from phone; to set this up, there are few steps.
  1. Setup phone's bluetooth with Lego brick. Use bluetooth find from phone, or from the Lejos menu.
  2. Once connected and confirm, that is done. If the brick can see the phone's name and phone can see the brick's name that is sufficient. It will not display "Paired".
  3. Then Install Java_DC's Batman class into the lego brick.
  4. Change the DEVICE_NAME in Android_DC. The DEVICE_NAME is the Lego brick's name, this can be found in options of i)Eclipse EV3Control ii)See the android logs for the debug message "device:" iii) Look into the phone and find the lego paired name.
  5. To exit on the brick, just click ENTER and it will exit.
  6. WARNING: Tried setup this before, but notice the localhost 10.0.0.1 no longer appears in lego brick, might be a bug.

### Others - To install the java classes in Java_DC, right-click on the class and select Run As->EV3 Lejos Program
Each file contains a comment to run it. Have fun! :)
