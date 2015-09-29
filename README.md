# CSC 540 - Project 1 Library database simulation
The final project will be a single maven project being accessed by a single UI interface which could CLI or GUI.
We will develop the functionality and then expose an API which can be leveraged from the UI.

The project is a basic JPA project generated using Maven. The `persistence.xml` needs to be edited to connect to a local instance of Oracle database which will be used for development.

## Eclipse integration
Clone the repository using the SSH link to faciliate the check ins without entering username and password repeatedly. This is a maven project which will require Maven to be installed on your dev machine.
To setup the project in eclipse locally, run the following set of commands
```
git clone git@github.com:muchhalsagar88/dbms-lib.git
cd dbms-lib/
mvn eclipse:eclipse
```
This will make the project comaptible to be imported into Eclipse. 

Now import the project into your Eclipse workspace using,
`File -> Import -> Existing Maven Projects` 
and select the project directory.

Replace the username and password in the `persistence.xml` file with your local Oracle user credentials. Create a new Oracle user for the same.

## Local Oracle instance
Oracle has stopped shipping 10g on its website. So for development purposes, we can use Oracle 11g express edition. I have checked the backward compatibility and we should be good whne we port to 10g.
Found a great [link](http://www2.hawaii.edu/~lipyeow/ics321/2014fall/installoracle11g.html) to install Oracle on Ubuntu, so check it if you are using it since Ubuntu is not a listed Linux distro.
For other OSs, fairly decent amount of Oracle support is available. It should be a cakewalk.

## Problems that you might face
1. The Oracle driver jar might not be a part of your local Maven repository. If thats the case, you will have an error in Eclipse saying that the jar can't be found. 
The jar would need to be added to your local Maven repo, follow this [link](http://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/) for detailed instructions about the same.

