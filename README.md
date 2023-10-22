# Pet Store API Automation

The API Automation framework is based on the following tools:

* Java 17
* Maven
* TestNG
* Rest Assured API

This framework automates the Pet store API from https://petstore.swagger.io/#/.

## Project Usage

Clone this project from the repository and run the following command to get started.

```bash
mvn install -Dmaven.test.skip=true
```

## Run Test

To run the regression tests, use any of the below option.

* Option 1: Run the following command from command prompt.
    ```bash
      mvn test
    ```
* Option 2 : Run the `testng.xml` file directly from any IDE (preferably IntelliJ Idea).

## Report

After the tests are executed, the reports get generated as html in `target/surefire-reports/index.html` and more
detailed report get generated as `target/surefire-reports/emailable-report.html`.

