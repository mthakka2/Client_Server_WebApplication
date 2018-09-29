## Building

```bash
git clone https://github.com/SER516/ProjectThree_Team09
cd ProjectThree_Team09
mvn package
```

## Running

The `mvn package` command will build the client into an executable fat JAR file that includes the library dependencies. To execute the client, simply double-click on the JAR file in `target/` or run the following command in a terminal window.

```bash
java -jar target/project3-1.0-jar-with-dependencies.jar
```
## Import Project on Eclipse
File >> Import >> Maven >> Existing Maven Projects >> Browse to Project Folder(Directory with pom.xml) 

## Code Coverage

To run the unit tests and generate a code coverage report, run

```bash
mvn cobertura:cobertura
```

This will generate a static HTML site in `target/site/`. Open `target/site/index.html` in a web browser to view the code coverage report.

