package tests;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import driverManager.DriverManager;


import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {

	public static String projectPath = System.getProperty("user.dir");
	private WebDriver driver;
	public Properties config =new Properties(); 
	public FileInputStream inputstream;
	public WebDriverWait wait;
	

	public String browser;
	//public ExcelUtilities excel;

	@BeforeSuite
	public void beforeSuite() throws Exception {

		setUpConfigFile();
		setUpRunModeForTestCases();
		
	}

	@AfterSuite
	public void afterSuite() throws Exception {

		

	}

	
	private void setUpConfigFile() {

		try 
		{
			inputstream=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			config.load(inputstream);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	private void setUpRunModeForTestCases() {

	}


	@SuppressWarnings("deprecation")
	public void openBrowser(String browser) throws MalformedURLException {

		DesiredCapabilities capability=null;

			ChromeOptions options=new ChromeOptions();

			switch(browser){

			case "chrome":

				WebDriverManager.chromedriver().setup();
				
				//System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.prompt_for_download", "true");

				chromePrefs.put("safebrowsing.enabled", "true"); 
				options.setExperimentalOption("prefs", chromePrefs);
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("--incognito");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(cap);
				break;

			case "chrome-headless":

				WebDriverManager.chromedriver().setup();
			
				//System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				HashMap<String, Object> chromePrefsHeadless = new HashMap<String, Object>();
				chromePrefsHeadless.put("profile.default_content_settings.popups", 0);
				chromePrefsHeadless.put("download.prompt_for_download", "true");

				chromePrefsHeadless.put("safebrowsing.enabled", "true"); 
				options.setExperimentalOption("prefs", chromePrefsHeadless);
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("--headless");
				options.addArguments("--incognito");
				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(caps);
				break;
				
			case "firefox":

				WebDriverManager.firefoxdriver().setup();
				
				//System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverExePath());
				FirefoxOptions FFoptions= new FirefoxOptions();
				FFoptions.addArguments("--incognito");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new FirefoxDriver(capabilities);
				break;

			default:
				
				WebDriverManager.chromedriver().setup();
				
			//	System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.setExperimentalOption("useAutomationExtension", false);
				//options.addArguments("--incognito");
				driver=new ChromeDriver(options);
				break;
			}

		DriverManager.setWebDriver(driver);
		maximizeWindow();
		setUpImplicitWait();
	}

	/*
	 * private void setUpOtherProperties() {
	 * 
	 * if(DriverFactory.getExecutionMode().equalsIgnoreCase("Remote")) {
	 * DriverFactory.setGridPath(config.getProperty("RemoteURL"));
	 * DriverFactory.setRemoteMode(config.getProperty("RemoteMode")); }
	 * 
	 * DriverFactory.setBrowser(config.getProperty("Browser"));
	 * DriverFactory.setTestDataLocation(config.getProperty("TestDataLocation"));
	 * DriverFactory.setWaitTime(Integer.parseInt(config.getProperty("WaitTime")));
	 * DriverFactory.setTestURL(config.getProperty("url"));
	 * DriverFactory.setScreenshotPath(config.getProperty("ScreenshotsPath"));
	 * DriverFactory.setFailedStepsScreenshots(config.getProperty(
	 * "FailedStepsScreenshots")); }
	 */
	

	public void navigateToURL(WebDriver driver, String url) {

		driver.get(url);
	}


	private void setUpImplicitWait() {

		DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	private void maximizeWindow() {

		DriverManager.getDriver().manage().window().maximize();
	}

	public void explicitWait(WebElement ele)throws Exception{

		wait=new WebDriverWait(DriverManager.getDriver(),10);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
}
