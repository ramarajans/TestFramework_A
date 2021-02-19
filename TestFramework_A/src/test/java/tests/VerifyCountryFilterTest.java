package tests;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;

import driverManager.DriverManager;
import pages.SignUpCAIPage;
import pages.VerifyCountryFilterPage;
import utils.Helper;


public class VerifyCountryFilterTest extends BaseTest{

	VerifyCountryFilterPage countryFilterVeri = null;

	@BeforeMethod
	public void beforeMethod() {
		
	}

	@Test(dataProvider = "data") //retryAnalyzer = RetryFailedTestCases.class
	public void verifyCountryFilterTest(String browser, String country) throws Exception {

		openBrowser(browser);
		navigateToURL(DriverManager.getDriver(),"https://viewpoint.glasslewis.com/WD/?siteId=DemoClient");	
		countryFilterVeri = new VerifyCountryFilterPage(DriverManager.getDriver());
		
		countryFilterVeri.checkCountryFilterExist();
		countryFilterVeri.enterCountryFieldValue(country);
		countryFilterVeri.selectcountryCheckbox(country);
		Thread.sleep(5000);
		countryFilterVeri.clickUpdateButton();
		Thread.sleep(5000);
		boolean verifyCountry = countryFilterVeri.verifyCountryValuesFiltered(country);
		Assert.assertEquals(verifyCountry, true);
	}

	@AfterMethod
	public void afterMethod() {

		if(DriverManager.getDriver()!=null) {
			//DriverManager.getDriver().close();
			DriverManager.getDriver().quit();
			System.out.println("Browser closed");
		}
	}

	
	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws IOException {

		File file = new File(".//src/test/resources/testdata.xlsx");
		FileInputStream fis = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum() ;
		int lastCellNum = sheet.getRow(0).getLastCellNum();

		Object[][] obj = new Object[lastRowNum][lastCellNum];

		for (int i = 0; i < lastRowNum; i++) {
			for (int k = 0; k < lastCellNum; k++) {
				obj[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		
		return  obj;
	}
}
