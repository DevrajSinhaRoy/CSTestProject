package apiRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/apitests",
                 glue = {"stepDefinitions"},
                 plugin = {"pretty", "html:target/cucumber-reports",
                         "json:target/cucumber-json-report/Cucumber.json",
                         "junit:target/cucumber-junit-reports/Cucumber.xml"},
                 monochrome = true)
public class ValidateTestRunner {

}
