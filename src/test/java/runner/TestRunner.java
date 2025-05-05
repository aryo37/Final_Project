package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "html:reports/cucumber-reports.html",
                "json:reports/cucumber-reports.json",
                "junit:reports/cucumber-reports/web-test-report.xml",
                "timeline:reports/cucumber-reports/timeline",
                "rerun:reports/cucumber-reports/rerun.txt"},

        glue = {"stepDef"},
        features = {"src/test/java/features"},
        tags = "@web or @api",
        monochrome = true
)
public class TestRunner {

}
