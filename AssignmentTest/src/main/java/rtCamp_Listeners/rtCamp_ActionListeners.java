package rtCamp_Listeners;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class rtCamp_ActionListeners {
	
	public WebDriver driver;
	JavascriptExecutor js=(JavascriptExecutor)driver;

    private static ExtentReports extent;
    private static ExtentSparkReporter htmlReporter;
	ExtentReports reports;
	Process ffmpegProcess;
	
	
	 	private FluentWait<WebDriver> wait;
	   public rtCamp_ActionListeners(WebDriver driver) {
		   
		   if (driver == null) {
	            throw new IllegalArgumentException("Driver must not be null");
	        }

	        this.driver = driver;
	        this.wait = new FluentWait<>(driver)
	            .withTimeout(Duration.ofSeconds(10))
	            .pollingEvery(Duration.ofMillis(500))
	            .ignoring(NoSuchElementException.class);

	        this.js = (JavascriptExecutor) driver;
	   }
	
	
	public String captureScreenshot(String methodName) {
		
		
		try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            // Properly instantiate the date object

            Calendar calendar = Calendar.getInstance();
            java.util.Date time = calendar.getTime();
            String timestamp = time.toString().replace(":", "").replace(" ", "_");
            String destination = System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\Screenshots\\" + methodName +"_" + timestamp + ".png";
            File finalDestination = new File(destination);
            FileUtils.copyFile(source, finalDestination);
            System.out.println("Screenshot captured and saved to: " + destination);
            return destination;   
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	public void ScrollDownTillBottom()
 	{
		
		 for (int i = 0; i < 5; i++) {
	            js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
	            // Add a short delay between scrolls to allow content to load
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
 	}
	
	public void ScrollUpTillTop()
	{
	    js.executeScript("window.scrollTo(0, -450)");
	}
	
	public static synchronized ExtentReports getInstance() {
        if (extent == null) {
        	  String reportPath = System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\TestReport\\LoginPage.html";
            htmlReporter = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            System.out.println("html report "+htmlReporter+ "report"+reportPath);
            
        }
        return extent;
 }
    public static synchronized void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
	
    
  
    public void startVideoRecording(String mode) throws IOException {
       
        String outputDirectory = System.getProperty("user.dir") + "/test-recordings/";
        
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String ffmpegPath = "C:\\Users\\Lenovo\\Downloads\\ffmpeg\\bin\\ffmpeg.exe"; 
        String outputFileName = outputDirectory + "test_recording_" + mode + ".mp4";
        String ffmpegCommand = "ffmpeg -y -video_size 1920x1080 -framerate 30 -f x11grab -i :0.0+0,0 " + outputFileName;

   
        ffmpegProcess = new ProcessBuilder(ffmpegCommand.split(" ")).start();
        System.out.println("Video recording started, saving to: " + outputFileName);
    }


    public void stopVideoRecording() {
        if (ffmpegProcess != null)
        {
            ffmpegProcess.destroy();
            System.out.println("Recording process stopped.");
        }
    }
}
