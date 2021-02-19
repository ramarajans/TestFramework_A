package utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import driverManager.DriverManager;

public class GenericUtils {

	WebDriverWait wait = null;
	public String driverPath = null;

	public Map<String,String> configMap = new HashMap<String,String>();
	public static String projectPath = System.getProperty("user.dir");

	/***
	 * Method to double click on a web element using Actions class
	 * @param ele
	 * @param fieldName
	 * @throws Exception
	 */
	public void doubleClick(WebElement ele, String fieldName)throws Exception{

		try {
			explicitWait(ele);
			new Actions(DriverManager.getDriver()).moveToElement(ele).doubleClick().perform();

			System.out.println(fieldName+ " is double clicked successfully"); 

		}catch (Exception e) {
			Assert.fail("Double click on "+fieldName+ " failed");
		}
	}

	/***
	 * Method to click on an element using Actions class
	 * @param ele
	 * @param fieldName
	 * @throws Exception
	 */
	public void actionsClick(WebElement ele, String fieldName)throws Exception{
		try {
			explicitWait(ele);
			new Actions(DriverManager.getDriver()).moveToElement(ele).click().perform();
			System.out.println(fieldName+ " is clicked successfully"); 
		}
		catch (Exception e) {
			
			Assert.fail("Click on "+fieldName+ " failed");
		}
	}

	/***
	 * Method to enter text in textbox/textarea
	 * @param ele
	 * @param value
	 * @param fieldName
	 * @throws Exception
	 */
	public void type(WebElement ele , String value, String fieldName)throws Exception{
		try {
			explicitWait(ele);
			ele.sendKeys(value);
			System.out.println("Entered text - "+value+" in "+fieldName+ " successfully");

		}catch (Exception e) {
			 
			Assert.fail("Inputting text - "+value+" in "+fieldName+ " failed");
		}
	}

	/***
	 * Method to click a web element
	 * @param ele
	 * @param fieldName
	 * @throws Exception
	 */
	public void click(WebElement ele, String fieldName)throws Exception{
		try {
			explicitWait(ele);
			ele.click();
			System.out.println(fieldName+ " is clicked successfully");

		}catch (Exception e) {
			Assert.fail("Click on "+fieldName+ " failed");
		}
	}

	/***
	 * Method to clear the text available in textbox/textarea
	 * @param ele
	 * @param fieldName
	 * @throws Exception
	 */
	public void clear(WebElement ele, String fieldName)throws Exception{
		try {
			explicitWait(ele);
			ele.clear();
			System.out.println(fieldName+ " is cleared successfully");
		}
		catch (Exception e) {
			Assert.fail("Clearing "+fieldName+ " failed");
		}
	}

	/*
	public boolean checkFieldExist1(By by, boolean expectedStatus, String fieldName)throws Exception{

		boolean fieldExist = false;

		try {
			DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			if(DriverManager.getDriver().findElements(by).size()!=0) {
				LogStatus.info(fieldName+ " exists!!");
				fieldExist = true;
			}
			else {
				LogStatus.info(fieldName+ " does not exist!!");
			}		
		}
		catch (Exception e) {
			LogStatus.fail("Check field existence of "+fieldName+ " failed");
			Assert.fail("Check field existence of "+fieldName+ " failed");
		}

		return fieldExist;
	}

	 */
	/***
	 * Method to check if a field is present in the web page.
	 * Returns true or false based on the expected status that is passed as the second parameter
	 * @param by
	 * @param expectedStatus
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public boolean checkFieldExist(By by, boolean expectedStatus, String fieldName)throws Exception{

		boolean fieldExist = false;

		try {
			DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			if(DriverManager.getDriver().findElements(by).size()!=0) {
				fieldExist = true;
			}

			if(fieldExist==expectedStatus) {
				if(fieldExist)
					System.out.println(fieldName+ " exists as expected");
				else
					System.out.println(fieldName+ " does not exist as expected");		
			}
			else {
				if(fieldExist) {
					Assert.fail(fieldName+ " exists");
				}
				else {
					Assert.fail(fieldName+ " does not exist");
				}
			}

		}catch (Exception e) {

			Assert.fail("Check field existence of "+fieldName+ " failed");

		}

		return fieldExist;
	}


	/***
	 * Method to switch to frame
	 * @param ele
	 * @throws Exception
	 */
	public void switchToFrame(WebElement ele)throws Exception{
		try {
			explicitWait(ele);
			String id = ele.getAttribute("id");
			DriverManager.getDriver().switchTo().frame(id);
			System.out.println("Switched to frame successfully");
		}
		catch (Exception e) {
			Assert.fail("Failed to switch to frame");
		}
	}

	/***
	 * Method to switch back to default content from frame
	 * @throws Exception
	 */
	public void switchToDefaultContent()throws Exception{
		try {
			DriverManager.getDriver().switchTo().defaultContent();
			System.out.println("Switched to default content from frame successfully");
		}
		catch (Exception e) {
			Assert.fail("Failed to switch to default content");
		}
	}

	public void keyPress(WebElement ele, Keys key)throws Exception{
		try {
			explicitWait(ele);
			ele.sendKeys(key);
		}
		catch (Exception e) {
			Assert.fail("Key Press failed");
		}
	}

	/***
	 * Method to wait for a particular web element to be visible. Throws Time out exception if the element is not visible
	 * @param ele
	 * @throws Exception
	 */
	public void explicitWait(WebElement ele)throws Exception{

		try {
			wait=new WebDriverWait(DriverManager.getDriver(),30);
			wait.until(ExpectedConditions.visibilityOf(ele));
		}
		catch (Exception e) {
			System.out.println("Timeout while waiting for the element");
		}
	}

	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver1) {
				return ((JavascriptExecutor) driver1).executeScript("return document.readyState").toString().equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 30);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println("Timeout waiting for Page Load Request to complete.");
		}
	}

	public static String getScreenshot(String fileName)
	{
		TakesScreenshot ts=(TakesScreenshot) DriverManager.getDriver();

		File src=ts.getScreenshotAs(OutputType.FILE);

		String path = projectPath+"//Screenshots//"+fileName+"_"+System.currentTimeMillis()+".png";

		File destination=new File(path);

		try 
		{
			FileUtils.copyFile(src, destination);
		} catch (IOException e) 
		{
			System.out.println("Capture Failed "+e.getMessage());
		}

		return path;
	}
	public boolean isAlertPresent() {
		try {
			DriverManager.getDriver().switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public int getColumnNumber(String tblXpath, String colName)throws Exception{

		int colNum = -1;
		int colCount = DriverManager.getDriver().findElements(By.xpath(tblXpath+"/thead/tr/th")).size();
		List<String> columnNames = new ArrayList<String>();

		for(int i=0;i<colCount;i++)
			columnNames.add(DriverManager.getDriver().findElement(By.xpath(tblXpath+"/thead/tr/th["+(i+1)+"]")).getText());

		colNum = columnNames.indexOf(colName);
		colNum = colNum+1;

		System.out.println("Column Number : "+colNum);
		return colNum;
	}

	public int getColumnNumber(WebElement tblXpath, String colName)throws Exception{

		int colNum = -1;
		int colCount = DriverManager.getDriver().findElements(By.xpath(tblXpath+"/thead/tr/th")).size();
		List<String> columnNames = new ArrayList<String>();

		for(int i=0;i<colCount;i++)
			columnNames.add(DriverManager.getDriver().findElement(By.xpath(tblXpath+"/thead/tr/th["+(i+1)+"]")).getText());

		colNum = columnNames.indexOf(colName);
		colNum = colNum+1;

		System.out.println("Column Number : "+colNum);
		return colNum;
	}

	public int getColumnCount(String tblXpath)throws Exception{

		int colCount = DriverManager.getDriver().findElements(By.xpath(tblXpath+"/thead/tr/th")).size();

		return colCount;
	}

	public int getRowCount(String tblXpath)throws Exception{

		int rowCount = DriverManager.getDriver().findElements(By.xpath(tblXpath+"/tbody/tr")).size();

		return rowCount;
	}
	
	public String getTableCellValue(WebElement tblXpath)throws Exception{

		String cellVal = tblXpath.getText();

		return cellVal;
	}

	public String generateDate(String dateFormat, String value)throws Exception{

		DateFormat df = new SimpleDateFormat(dateFormat);//"ddMMyyHHMMss"
		Date date = new Date();
		String today = df.format(date);
		return today;
	}
}
