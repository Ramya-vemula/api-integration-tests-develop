# api-integration-tests

## About Project
This project is build using TestNG framework with gradle.

### Prerequisites
- Make sure JAVA_HOME is set correctly for your mac
- Make sure below two test accounts added into the database, as these are used in tests.
  - rohith.vitta@rungway.com
  - editUser@rungway.com
- Update the above test user accounts in the dev.properties, local.properties and staging.properties files    

### Download repository
- Git clone api-integration-tests repo
- Once you have clone the repo make sure all the dependencies are imported

### Run api-integration-tests via jenkins
- Go to api-integaration-tests on jenkins
- Click on develop > Build with Parameters
- Select an environment(dev, staging or prod) > run

### Running tests by environment
- Make sure properties file for environment is updated 
- Point loadEnvironment method in **TestSetup** class to desired properties file 
(eg: dev, local or staging)and run tests via IDE or command line normally.    

### Run restAssuredTests via IDE
- Right click on a test and select **run** from the list  
- Run below via terminal
    - gradle clean test 

### Trouble shooting on failed restAssuredTests
1. Check the logs, look for error.
2. If running test via jenkins allure report should have the error for all the failed tests. 
   This should give enough clarity on the test failure.
3. If you still cannot find out issue **SHOUT AT ROHITH**
# api-integration-tests-develop
