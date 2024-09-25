package rtCamp.AssignmentTest;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import rtCamp_Listeners.rtCamp_ActionListeners;
import rtCamp_PageElement.rtCampLoginPage;




public class rtCampAssignmentScenario
{
			String url="https://www.saucedemo.com/";
			String username="standard_user";
			String password="secret_sauce";
			String fname="Ashwin";
			String lname="Mourya";
			String zip="44001";
			
			private rtCamp_ActionListeners action;
			rtCampLoginPage loginpg;
		    WebDriver driver;
	        ExtentReports extent;
			ExtentTest test;
			
			@BeforeClass 
			@Parameters({"headless"}) 
		    public void setup( @Optional("false") String headlessMode) throws IOException
			{
				
		        WebDriverManager.chromedriver().setup();

		        ChromeOptions options = new ChromeOptions();
		        if (Boolean.parseBoolean(headlessMode))
		        {
		            options.addArguments("--headless"); 
		            options.addArguments("--disable-gpu"); 
		        }

		        driver = new ChromeDriver(options);
		        driver.manage().window().maximize();
		        driver.get(url);
		        
		        
		        rtCamp_ActionListeners action =new rtCamp_ActionListeners(driver);
		        action.startVideoRecording(headlessMode);
		    }

			    //Verify the sorting order displayed for Z-A on the “All Items” page.
				@Test(priority=1)
				public void TC1_VerifyLoginPage_WithValidUsername_ValidPassword() throws InterruptedException
				{
					
					loginpg = new rtCampLoginPage(driver);
					loginpg.LoginPage(username, password);
					Thread.sleep(2000);
				}
		
				
				//Verify the price order (high-low) displayed on the “All Items” page.
				@Test(priority=2,dependsOnMethods="TC1_VerifyLoginPage_WithValidUsername_ValidPassword")
				public void TC2_VerifyTheSortingOrder_displayedForZA_OnAllItemsPage() throws InterruptedException
				{
					
				    loginpg = new rtCampLoginPage(driver);
					loginpg.AlphabetShort();
					Thread.sleep(2000);
					
				}               //after short by alphabetical order then page need to refresh
				
				
				
				//Add multiple items to the card and validate the checkout journey.
				@Test(priority=3,dependsOnMethods="TC1_VerifyLoginPage_WithValidUsername_ValidPassword")
				public void TC3_VerifyTheSortingPriceOrder_displayedForHigh_Low_OnAllItemsPage() throws InterruptedException
				{
					
					loginpg = new rtCampLoginPage(driver);
					loginpg.PriceShort();
					Thread.sleep(2000);
					
				}     //after short by sorting amount then page need to refresh
				
				
				//full process of from adding item till checkcout
				@Test(priority=4,dependsOnMethods="TC1_VerifyLoginPage_WithValidUsername_ValidPassword")
				public void TC4_VerifyToAddMultipleItemsToTheCard_ValidateTheCheckout() throws InterruptedException
				{
					
					loginpg = new rtCampLoginPage(driver);
					//Thread.sleep(2000);
					loginpg.addItemsToCart();
					Thread.sleep(2000);
					//action.ScrollDownTillBottom();
					loginpg.validateCartItems();
					Thread.sleep(2000);
					loginpg.proceedToCheckout(fname, lname, zip);
					Thread.sleep(2000);
					loginpg.validateCheckoutItems();
					Thread.sleep(2000);
					loginpg.Logout();
					Thread.sleep(1000);
					
				}
				
				
			    @AfterClass
			    public void tearDown() throws IOException {
			        // Quit the browser and stop FFmpeg video recording
			        driver.quit();
			        rtCamp_ActionListeners action=new rtCamp_ActionListeners(driver);
			        action.stopVideoRecording();
			        
			    }


}
