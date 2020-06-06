# GAWP
**Creating Branch In IntelliJ**
![Recordit GIF](http://g.recordit.co/G74SfPEakd.gif)
**Creating Branch In Github Desktop**
![Recordit GIF](http://g.recordit.co/pJQH4mJxiF.gif)

* You no longer need a running oracle database. Project is set up to work on a local db that is stored in the data file.
* If you want to use the oracle VM machine, after you install it (see the following lines) comment the h2db lines in the src/main/resources/application.yml and uncomment the oracle db lines in the same file. I have shown which lines are h2db lines and which lines are oracledb lines in the .yml file.
To install oracle db to the virtualbox;
* Download the ovm file from here: https://www.oracle.com/database/technologies/databaseappdev-vm.html
* After the download, click to file -> import appliance in the virtualbox to set up the oracle database.
