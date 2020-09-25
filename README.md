# ClinicManagmentSystemFX
This is pretty simple project in JAVA, that is representing local clinic managment system. Written in MVC pattern. Connection to the MySQL database. 
Warning - software is not localized (just polish language!)

# TechStack
JAVA 13,
Maven,
JavaFX,
MySQL

# How to run
That is simple, just open project in IDE and click run ;)
Or ofcourse open jar file.
Put AdminPanel into your WWW server directory to have access from your browser.
But remember - to work properly, app needs working database. In directory DB, I put MySQL database file, with some example data to work with. You can delete them and write your own or work with those included. If you need, just import database on some hosting or DB server - then just edit connection class with correct connection data.

# How it works
First what you will see, will be the Login window, complete files with account data and hit Login. You can also open registration form or open administration site in your browser.
Just hit proper button.

After logging you will see dashboard with included options, which are related to your account type (patient, doctor or administrator). For example for patient you can see just your data, no one else. But as a doctor you can also check data for all patients in DB.

As admin there are less options. There are features like account activation or creation new account.

Have fun!
# LastChanges
14.09.2020
