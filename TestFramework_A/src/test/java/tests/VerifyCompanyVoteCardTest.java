package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import driverManager.DriverManager;
import pages.VerifyCompanyVoteCardPage;

public class VerifyCompanyVoteCardTest extends BaseTest{

	VerifyCompanyVoteCardPage companyVoteCardVeri = null;

	@BeforeMethod 
	public void beforeMethod() {

	}

	@Test(dataProvider = "data")
	public void verifyCompanyVoteCardTest(String browser) throws Exception {

		openBrowser(browser);
		navigateToURL(DriverManager.getDriver(),"https://viewpoint.glasslewis.com/WD/?siteId=DemoClient");
		companyVoteCardVeri = new VerifyCompanyVoteCardPage(DriverManager.getDriver());
		companyVoteCardVeri.checkCountryFilterExist();
		companyVoteCardVeri.clickCountryFilterResetButton();
		
		boolean isFiledExist = companyVoteCardVeri.checkCompanyNameActivision();
		Assert.assertEquals(isFiledExist, true);
		
		companyVoteCardVeri.clickOnCompanyNameActivision();
		Thread.sleep(3000);
		companyVoteCardVeri.checkCompanyNameBanner();
	}

	@AfterMethod public void afterMethod() {

		if(DriverManager.getDriver()!=null) 
		{ 
			DriverManager.getDriver().quit();
			System.out.println("Browser closed");
		} 
	}
	
	
	@DataProvider(name = "data")
	public Object[][] dataSupplier() throws IOException {

		File file = new File(".//src/test/resources/testdata.xlsx");
		FileInputStream fis = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("CompanyVoteCard");
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
