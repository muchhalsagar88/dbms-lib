# CSC 540 - Project 1 Library database simulation
The final project will be a single maven project being accessed by a single UI interface which could CLI or GUI.
We will develop the functionality and then expose an API which can be leveraged from the UI.

The project is a basic JPA project generated using Maven. The `persistence.xml` needs to be edited to connect to a local instance of Oracle database which will be used for development.

## Dependencies
1. Maven 
2. Oracle 11g Express edition
3. Eclipse, probably the latest edition but doesn't matter much

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
### Add the Oracle instance to Eclipse
I will update this section with a step by step gif in some time. In the meanwhile to briefly summarize it, 

1. Change the Eclipse perspective to JPA
2. Right click on the `Database Connections` window to setup a new connection
3. Select `Oracle` as your Connection Type.
4. Select the Driver named `Oracle Thin Driver` with the latest version. Add the previously used ojdbc6.jar to this driver as well.
5. Change the SID(xe, by default) Host(localhost, by default) and Port(1521, by default). Input your user credentials and `Test your connection`.
6. Click `Finish` to complete adding the Database Connection.

## Problems that you might face
1. The Oracle driver jar might not be a part of your local Maven repository. If thats the case, you will have an error in Eclipse saying that the jar can't be found. 
The jar would need to be added to your local Maven repo, follow this [link](http://www.mkyong.com/maven/how-to-add-oracle-jdbc-driver-in-your-maven-local-repository/) for detailed instructions about the same.

2. When you view the code in Eclipse, the annotation `@Table` is the entity might show you a compliation error stating that the table is not found. Ignore that, since Eclipse takes a database screenshot when you initially connect to it. So any new tables added via code might not be present in the screenshot. Refreshing the conneection will take care of it, otherwise ignore it.

