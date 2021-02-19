package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.GenericUtils;

public class VerifyCountryFilterPage {

	String con = "Bermuda";
	@FindBy(id="txt-multiselect-static-search-CountryFilter") 
	WebElement searchCountryFilter;

	By searchCountryFilterBy = By.id("txt-multiselect-static-search-CountryFilter");

	//label[text()='Belgium']//parent::div
	//label[text()='Belgium']//preceding-sibling::input
	@FindBy(xpath="//label[text()='Belgium']//parent::div")
	WebElement searchCountry_Belgium_Checkbox;

	@FindBy(id="btn-update")
	WebElement searchCountryFilter_UpdateBtn;

	@FindBy(xpath="//div[@class='k-grid-header-wrap k-auto-scrollable']//table")
	WebElement countryHeaderTbl;

	String headerTblXpath = "//div[@class='k-grid-header-wrap k-auto-scrollable']//table";
	By headerTbl = By.xpath(headerTblXpath);
	
	@FindBy(xpath="//div[@class='k-grid-content k-auto-scrollable']//table")
	WebElement countryContentTbl;

	String contentTblXpath = "//div[@class='k-grid-content k-auto-scrollable']//table";
	By contentTbl = By.xpath(contentTblXpath);
	
	
	GenericUtils genUtils = new GenericUtils();
	private WebDriver driver=null;

	public VerifyCountryFilterPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}


	public void checkCountryFilterExist()throws Exception{

		genUtils.checkFieldExist(searchCountryFilterBy, true, "Country Filter Search");
	}

	public void enterCountryFieldValue(String country)throws Exception{

		genUtils.type(searchCountryFilter, country, "Country Filter Search");

	}

	public void selectcountryCheckbox(String country)throws Exception{

		driver.findElement(By.xpath("//label[text()='"+country+"']//parent::div")).click();
		//genUtils.actionsClick(searchCountry_Belgium_Checkbox, "Belgium country checkbox");

	}

	public void clickUpdateButton()throws Exception{

		genUtils.click(searchCountryFilter_UpdateBtn, "Update Button");

	}
	public boolean verifyCountryValuesFiltered(String country) throws Exception {

		boolean isCountryFilterWorking = true;
		int columnNumber_Country = genUtils.getColumnNumber(headerTblXpath, "Country");

		System.out.println("headerTblXpath ==> "+headerTblXpath);
		System.out.println("contentTblXpath ==> "+contentTblXpath);
		System.out.println("columnNumber_Country : "+columnNumber_Country);
		
		int rowCount = genUtils.getRowCount(contentTblXpath);
		System.out.println("rowCount : "+rowCount);
		
		String cellValueCountry = null;

		for(int g=1;g<=rowCount;g++){

			cellValueCountry = driver.findElement(By.xpath(contentTblXpath+"//tr["+g+"]/td["+columnNumber_Country+"]")).getText();
			//cellValueCountry = genUtils.getTableCellValue(contentTblXpath+"//tr["+g+"]/td["+columnNumber_Country+"]");
			if(!country.equals(cellValueCountry)) {
				isCountryFilterWorking = false;
				System.out.println("Expected Country Value -> "+country);
				System.out.println("Country value present in table -> "+cellValueCountry);
				break;
			}

		}

		return isCountryFilterWorking;
	}
}
