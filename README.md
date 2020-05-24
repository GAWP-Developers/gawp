# Spring Boot Oracle Example

* To run this example you will need to download and install the Oracle JDBC driver ojdbc7.jar from https://www.oracle.com/database/technologies/jdbc-drivers-12c-downloads.html.
* To install, move the ojdbc7.jar to the project folder. Then, open a cmd/terminal in the project folder and paste `mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar`
* You also need a running oracle database. I recommend setting up in a virtualbox. Download the ovm file from here: https://www.oracle.com/database/technologies/databaseappdev-vm.html
* After the download, click to file -> import appliance in the virtualbox to set up the oracle database.
