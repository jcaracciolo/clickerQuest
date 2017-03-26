This is a multi-module project for Web Aplication Proyects, ITBA.
This aplicacion is an Idle game in development.

### Steps to bootstrap in IntelliJ IDEA
- Clone the project
- Start IntelliJ Idea and select import project
- Select the root folder of the git repository
- From the three available options, choose Maven
- Select the following options:
  * Import Maven projects automatically
  * Create IntelliJ IDEA modules for aggregator projects
  * Create module groups for mult-module Maven projects
  * Keep source and test folders on reimpor
  * Exclude build directory
  * User Maven output directories: `Detect automatically`
  * Automatically download: `Sources` and `Documentation`
- Continue with import
- Select the webapp/src/main/java folder and rigth click -> Mark directory as -> Sources Root

### Configure jetty:

- Download the plugin [from this site](https://plugins.jetbrains.com/plugin/download?updateId=22888) (if you already have jetty Runner as plugin uninstall it)
- File -> Settings -> Plugins -> install from disk
- Select the downloaded zip
- Restart IDEA
- Run -> Edit Configurations
- Select jetty Runner
- Setup Path = '\' , Webapp Folder = 'webapp/src/main/webapp' , Classes Folder = 'webapp/target/classes'
- Down, before Launch, run Maven Goal with the command "clean compile"
- Done
IMPORTANT: DO NOT UPDATE THE JETTY PLUGIN OR IT WILL NOT WORK

### Configure Postgres:
- Install postgres (sudo apt-get install postgresql)
- Start postres server (sudo service postgresql start)
- Change to user "postgres" (sudo su postgres)
- Create root user (createusername root) (si tira error no pasa nada)
- Create main database (createdb clickerQuest -O root)
- Enter psql (psql)
- Change root password (ALTER USER root WITH PASSWORD 'root')
- Type ( \g ) to send the query
- "ALTER ROLE" Means it was successful
- ( \q ) Exit psql
- ??
- Profit!!

