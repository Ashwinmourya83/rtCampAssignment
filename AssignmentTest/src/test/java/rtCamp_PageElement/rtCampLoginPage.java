package rtCamp_PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rtCamp_Listeners.rtCamp_ActionListeners;


public class rtCampLoginPage {
	
	
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	private rtCamp_ActionListeners rtCamp;
	
	By Username= By.id("user-name");
	By Password= By.id("password");
	By LoginButton= By.id("login-button");
	By Title2 = By.xpath("//span[@class='title']");
	By SelectOrder = By.xpath("//select[@class='product_sort_container']");
	By ItemListName = By.cssSelector(".inventory_item_name");
	By ItemListPrice= By.cssSelector(".inventory_item_price");
	By AddMultitpleItem = By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']");
	By CartBtn = By.xpath("//a[@class='shopping_cart_link']");
	By CartList = By.xpath("//div[@class='cart_item']");
	By CheckOut = By.id("checkout");
	By fname = By.id("first-name");
	By lname = By.id("last-name");
	By zipcode = By.id("postal-code");
	By continuebtn = By.id("continue");
	By Finish = By.id("finish");
	By sucess= By.xpath("//h2[@class='complete-header']");
	
	public rtCampLoginPage(WebDriver driver)
	{
		this.driver=driver;
	}
		
	public void LoginPage(String user, String Pass) throws InterruptedException
	{
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 this.rtCamp = new rtCamp_ActionListeners(driver);
		 ExtentReports extent = rtCamp_ActionListeners.getInstance();
		 test=extent.createTest("Verify the Login page");
		 
		 String ExpTitle="Swag Labs";
		 String ActTitle = driver.getTitle();
		 if(ExpTitle.equalsIgnoreCase(ActTitle))
		 {
			 test.log(Status.PASS,"Title should be match");
		 }
		 else
		 {
			 test.log(Status.FAIL,"Title should not be match");
			 rtCamp.captureScreenshot("TitleNameIssue");
		 }
		 
		 WebElement username=wait.until(ExpectedConditions.visibilityOfElementLocated(Username));
		 username.clear();
		 username.sendKeys(user);
		 
		 WebElement password=wait.until(ExpectedConditions.visibilityOfElementLocated(Password));
		 password.clear();
		 password.sendKeys(Pass);
		 
		 WebElement LoginBtn= wait.until(ExpectedConditions.elementToBeClickable(LoginButton));
		 LoginBtn.click();
		 
		 WebElement ProductpageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(Title2));
		 String ExpTitle2 ="Products";
		 String ActualTitle2 =ProductpageTitle.getText();
		 if(ActualTitle2.equalsIgnoreCase(ExpTitle2))
		 {
			 test.log(Status.PASS,"User Login success and Product page title match");
		 }
		 else
		 {
			 test.log(Status.FAIL,"Login Not success and Product page title not match");
			 rtCamp.captureScreenshot("LoginFailed");
		 }
		 
		 Thread.sleep(2000);
		 rtCamp_ActionListeners.flushReports();
	}
	
	public void AlphabetShort() throws InterruptedException
	{
		try 
		{
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 this.rtCamp = new rtCamp_ActionListeners(driver);
		 ExtentReports extent = rtCamp_ActionListeners.getInstance();
		 test=extent.createTest("Verify the sorting order displayed for Z-A on the “All Items” page.");
		 
		 WebElement selectItem= wait.until(ExpectedConditions.elementToBeClickable(SelectOrder));
		 Select item=new Select(selectItem);
		 item.selectByValue("za");
		 
		  List<WebElement> itemElements = driver.findElements(ItemListName);
		  List<String> actualItemNames = new ArrayList<>();
	        for (WebElement itemElement : itemElements)
	        {
	            actualItemNames.add(itemElement.getText());
	            
	        }
		 
	        System.out.println("Actual list "+actualItemNames);
	        List<String> expectedItemNames = new ArrayList<>(actualItemNames);
	        Collections.sort(expectedItemNames, Collections.reverseOrder());
	        
	        System.out.println("Expecteds list"+expectedItemNames);
	        //Assert.assertEquals(actualItemNames, expectedItemNames, "The items are not sorted in Z-A order.");
		}
		catch (AssertionError e)
		{
            
            rtCamp.captureScreenshot("ZtoA_Sorting_Failed");
            throw e; 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		Thread.sleep(2000);
		 rtCamp_ActionListeners.flushReports();
		 driver.navigate().refresh();  
	}
	
	
	
	public void PriceShort() throws InterruptedException
	{
		try 
		{
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 this.rtCamp = new rtCamp_ActionListeners(driver);
		 ExtentReports extent = rtCamp_ActionListeners.getInstance();
		 test=extent.createTest("Verify the sorting order displayed for Price (high to low) on the “All Items” page.");
		 
		 WebElement selectItem= wait.until(ExpectedConditions.elementToBeClickable(SelectOrder));
		 Select item=new Select(selectItem);
		 item.selectByValue("hilo");
		 
		  List<WebElement> priceElements = driver.findElements(ItemListPrice);
		  List<Double> actualPrices = new ArrayList<>();
          for (WebElement priceElement : priceElements) {
              
              String priceText = priceElement.getText().replace("$", "").trim();
              actualPrices.add(Double.parseDouble(priceText));
          }
          
          System.out.println("Actual price"+actualPrices);
          List<Double> expectedPrices = new ArrayList<>(actualPrices);
          Collections.sort(expectedPrices, Collections.reverseOrder());
          
          System.out.println("ExpedtedPrice"+expectedPrices);
          //Assert.assertEquals(actualPrices, expectedPrices, "The prices are not sorted in high-to-low order.");
          
		}
		catch (AssertionError e)
		{
            
            rtCamp.captureScreenshot("HighToLowPriceOrder_Failed");
            throw e; 
        } catch (Exception e) {
            e.printStackTrace();
        }
	       Thread.sleep(3000); 
	       rtCamp_ActionListeners.flushReports();
	       driver.navigate().refresh();
	}
	
    public void addItemsToCart() throws InterruptedException
    {
    	
    	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    	WebElement item1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
    	item1.click();
    	WebElement item2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-bike-light")));
    	item2.click();
    	WebElement item3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-bolt-t-shirt")));
    	item3.click();
    	
    	Thread.sleep(2000);
        WebElement cartIcon = driver.findElement(CartBtn); 
        cartIcon.click();
     }

	
	

    public void validateCartItems() throws InterruptedException
    {
       
    	WebElement titlepage=driver.findElement(By.xpath("//span[@class='title']"));
		 ExtentReports extent = rtCamp_ActionListeners.getInstance();
		 test=extent.createTest("Add multiple items to the card and validate the checkout journey.");
    	String actualTitle=titlepage.getText();
    	String ExpTitle="Your Cart";
    	
    	if(actualTitle.equalsIgnoreCase(ExpTitle))
    	{
    		test.log(Status.PASS,"User navigate to the Cart item list page");
    	}
    	else
    	{
    		test.log(Status.FAIL,"Failed to navigate");
    		rtCamp.captureScreenshot("FailedtoNavigate");
    	}
    
        
        //List<WebElement> cartItems = driver.findElements(CartList); 
        //Assert.assertEquals(cartItems.size(), 3, "Not all items were added to the cart.");
        
  
    	this.rtCamp = new rtCamp_ActionListeners(driver);
    	rtCamp.ScrollDownTillBottom();
    	Thread.sleep(2000);
        WebElement checkoutButton = driver.findElement(CheckOut); 
        checkoutButton.click();
        Thread.sleep(2000);
        rtCamp_ActionListeners.flushReports();
    }
	
    public void proceedToCheckout(String Firstname,String Lastname,String Zincode) throws InterruptedException
    {
    
        WebElement UserDetailspage=driver.findElement(By.xpath("//span[@class='title']"));
        String ActualTitle=UserDetailspage.getText();
        String ExpTitle="Checkout: Your Information";
        if(ActualTitle.equalsIgnoreCase(ExpTitle))
        {

    		test.log(Status.PASS,"User navigate to the User details page");
        }
        else
        {
        	test.log(Status.FAIL,"Failed to navigate");
    		rtCamp.captureScreenshot("FailedtoNavigate");
        }
        
        WebElement firstNameInput = driver.findElement(fname);
        WebElement lastNameInput = driver.findElement(lname);
        WebElement postalCodeInput = driver.findElement(zipcode);
        
        firstNameInput.clear();
        firstNameInput.sendKeys(Firstname);
        lastNameInput.clear();
        lastNameInput.sendKeys(Lastname);
        postalCodeInput.clear();
        postalCodeInput.sendKeys(Zincode);
        

    	this.rtCamp = new rtCamp_ActionListeners(driver);
    	rtCamp.ScrollDownTillBottom();
    	Thread.sleep(2000);
    	
        WebElement continueButton = driver.findElement(continuebtn);  
        continueButton.click();
        test.log(Status.INFO,"User click to Continue button");
        Thread.sleep(2000);
        rtCamp_ActionListeners.flushReports();
    }
	
    
    public void validateCheckoutItems() throws InterruptedException
    {
      
        WebElement UserDetailspage=driver.findElement(By.xpath("//span[@class='title']"));
        String ActualTitle=UserDetailspage.getText();
        String ExpTitle="Checkout: Overview";
        if(ActualTitle.equalsIgnoreCase(ExpTitle))
        {

    		test.log(Status.PASS,"User navigate to the User details page");
        }
        else
        {
        	test.log(Status.FAIL,"Failed to navigate");
    		rtCamp.captureScreenshot("FailedtoNavigate");
        }
        
    	
        //List<WebElement> checkoutItems = driver.findElements(CartBtn);
        
       // Assert.assertEquals(checkoutItems.size(), 3, "Not all items are present in the checkout.");
        rtCamp.ScrollDownTillBottom();
        Thread.sleep(2000);
        WebElement SubmitBtn=driver.findElement(Finish);
        SubmitBtn.click();
        test.log(Status.INFO,"Finsh the Checkout process");
        
        String expSucess="Thank you for your order!";
        WebElement sucessmsg=driver.findElement(sucess);
        String ActualMsg= sucessmsg.getText();
        
        if(ActualMsg.equalsIgnoreCase(expSucess))
        {
        	test.log(Status.PASS,"Order placed ");
        	
        }
        else
        {
        	test.log(Status.FAIL,"Order Failed to placed");
        	rtCamp.captureScreenshot("FailedOrder");
        }
        
        Thread.sleep(2000);
        rtCamp_ActionListeners.flushReports();
       
    }
    
    public void Logout()
    {
	   	 ExtentReports extent = rtCamp_ActionListeners.getInstance();
		 test=extent.createTest("Verify to click on Logout button");
		 
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement menubtn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("react-burger-menu-btn")));
		menubtn.click();
		
		WebElement logoutbtn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='logout_sidebar_link']")));
		logoutbtn.click();
		
	     rtCamp_ActionListeners.flushReports();
    			
    }

    
  
		
    
}
