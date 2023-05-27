# Library
Library database task

## How to compile
Run the following command:
`./gradlew clean build`

## How to run

- Make sure you changed `application.properties` in resource folder, by inserting into it your own properties and then **compiled** it.
- To run the program you can run this command after compiling and getting *.jar* file: `java -jar hospital-database-project-0.0.1.jar`
- Make sure to run `dump.sql` file that located in the root directory to create the tables.
- You also can add `--customProperties` argument that points to some **custom.properties** file and properties from that file will be used to run the program (`java -jar hospital-database-project-0.0.1.jar --refreshInsertions --customProperties=./otherFolder/example.properties`).

Structure of the properties file should be like that:
```bash
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://link:port/database_name
db.user=username
db.password=userPassword
db.connectionNumber=numberOfConnectionsInPull
```

Enjoy the program :)
Created by Yehor Chevardin.