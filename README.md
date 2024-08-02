## UCW Browser Automation Framework

### Setup
- JDK 17
- Gradle

### Build
Run command `gradle build`

### Test configuration
All test configuration in `config.json`, consider to modify following properties
- showReportAfterTest
- headless
- environment: dev, test, training, stag
- timeout
- systemYear: use current year or the year value in system clock

### Run test
- `gradle test -Psuite=smoke` ( run smoke suite on dev environment as default)
- `gradle test -Psuite=regression -Denv=TRAINING` ( run regression suite on training environment )
- `gradle test --tests ucw.tests.policycenter.PolicyCancelTest` ( run single test class)
- `gradle test --tests ucw.tests.policycenter.PolicyReinstatementTest.test_insurerValidation` ( run single test method)