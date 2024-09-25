package rtCamp.AssignmentTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CrossBrowsing {


	  private static WebDriver driver;

	    public static WebDriver getDriver(String webbrowser) {
	        if (driver == null) {
	            String browserType = System.getProperty("browser",webbrowser ); // Default to chrome if not specified

	            switch (browserType.toLowerCase()) {
	                case "chrome":
	                    WebDriverManager.chromedriver().setup();
	                    driver = new ChromeDriver();
	                    break;
	                case "firefox":
	                    WebDriverManager.firefoxdriver().setup();
	                    driver = new FirefoxDriver();
	                    break;
	                case "edge":
	                    WebDriverManager.edgedriver().setup();
	                    driver = new EdgeDriver();
	                    break;
	                default:
	                    throw new IllegalArgumentException("Browser not supported: " + browserType);
	            }

	            driver.manage().window().maximize();
	            // Add any other setup code here
	        }
	        return driver;
	    }
}
