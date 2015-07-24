
Super Quick Start Guide
1) cd into directory you unzipped
2) Run application in a terminal: java -jar comp6126.jar

Doing this will use a preconfigured freely hosted mysql database I've already initialized with my table creation sql and my data initialization sql.  However, that free database does not allow me to install my triggers, so some conditions will not be checked on insert/update that otherwise would.  If that is important to your analysis...

Quick Start Guide
1) cd into directory you unzipped
3) Edit config.properties to point at local MySQL instance
4) Create Tables by running createTables.sql in local MySQL instance
5) Initialize Data by running initData.sql in local MySQL instance
6) Install Triggers by running installTriggers.sql in local MySQL instance
7) Run application in a terminal: java -jar comp6126.jar

* Note - initData.sql must be run before installTriggers.sql as there are inserts in there that would not work if the triggers were in place due to the temporal nature of some of the checks.

You will want to run with a fairly wide terminal for best results

For full details on the application, please see alf0028_report.docx

