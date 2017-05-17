This is a multi-module project for Web Application Proyects, ITBA.

This application is an Idle game in development.

## Steps to bootstrap in IntelliJ IDEA
1. Clone the project
2. Start IntelliJ Idea and select import project
3. Select the root folder of the git repository
4. From the three available options, choose Maven
5. Select the following options:

  - [ ] Import Maven projects automatically
  - [ ] Create IntelliJ IDEA modules for aggregator projects
  - [ ] Create module groups for multi-module Maven projects
  - [ ] Keep source and test folders on re-import
  - [ ] Exclude build directory
  - [ ] User Maven output directories: `Detect automatically`
  - [ ] Automatically download: `Sources` and `Documentation`

6. Continue with import
7. Select the `webapp/src/main/java` folder and right click, select `Mark directory as -> Sources Root`

## Configure Jetty:

1. Download the plugin [from this site](https://plugins.jetbrains.com/plugin/download?updateId=22888) (if you already have jetty Runner as plugin uninstall it)
2. If you are a Mac user, jump straight to the steps [described in this link](https://www.jetbrains.com/help/idea/2016.3/installing-updating-and-uninstalling-repository-plugins.html)
3. File -> Settings -> Plugins -> install from disk
4. Select the downloaded zip
5. Restart IDEA
6. Run -> Edit Configurations
7. '+' -> select Jetty Runner -> name it 'Development'
8. Setup Path = '\' , Webapp Folder = 'webapp/src/main/webapp' , Classes Folder = 'webapp/target/classes'
9. '+' -> select Jetty Runner -> name it 'Production'
10. Setup Path = '\' , Webapp Folder = 'webapp/src/main/webapp' , Classes Folder = 'webapp/target/classes'
11. Env Args -> Name: 'spring.profiles.active' Value: 'production'
12. Down, before Launch, run Maven Goal with the command "clean compile"
13. Done

IMPORTANT: DO NOT UPDATE THE JETTY PLUGIN OR IT WILL NOT WORK

## Configure Postgres:

1. Install postgres (sudo apt-get install postgresql)
2. Start postres server (sudo service postgresql start)
3. Change to user "postgres" (sudo su postgres)
4. Create root user (createusername root) (si tira error no pasa nada)
5. Create main database (createdb clickerQuest -O root)
6. Enter psql (psql)
7. Change root password (ALTER USER root WITH PASSWORD 'root')
8. Type ( \g ) to send the query
9. "ALTER ROLE" Means it was successful
10. ( \q ) Exit psql
11. ??
12. Profit!!


## Deployment instructions

1. Set run as "Deploy"
2. ```mvn clean package```
3. Look for .war in `webapp/target`and rename it to `app.war`
4. `sftp user@pampero.itba.edu.ar`
5. Inside of sftp, run `put path/to/app.war`
6. Close session of sftp
7. Run `ssh user@pampero.itba.edu.ar`
8. Inside of ssh: `sftp paw-2017a-4@10.16.1.110`
9. Password is: `ooc4Choo`
10. Inside of sftp run:

```
cd web/
put path/to/app.wr/in/pampero
```


## Available routes

 * `/login`
    * GET
    * POST
 * `/register`
    * GET
    * POST
 * `/create`
    * GET
    * POST

The following routes need authentication:

* `/game`
    * GET

* `/buyFactory`
    * POST

* `/upgradeFactory`
    * POST

* `/buyFromMarket`
    * POST

* `/sellToMarket`
    * POST

* `/errors`
    * POST
