package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.GenericUtils;

public class VerifyCompanyVoteCardPage {

	
	@FindBy(id="txt-multiselect-static-search-CountryFilter") 
	WebElement searchCountryFilter;

	By searchCountryFilterBy = By.id("txt-multiselect-static-search-CountryFilter");

	@FindBy(xpath="//fieldset[@class='fieldset-county']//following-sibling::div//button[@id='btn-close']") 
	WebElement searchCountryFilterReset;
	
	@FindBy(xpath="//span[@class='k-dropdown-wrap k-state-default']/span[@class='k-select']") 
	WebElement itemsPerPageArrow;
	
	@FindBy(xpath="//ul[@class='k-list k-reset']/li[text()='150']") 
	WebElement itemsPerPage;
	
	@FindBy(xpath="//a[text()='Activision Blizzard Inc']") 
	WebElement activisionLink;
	
	By activisionLinkBy = By.xpath("//a[text()='Activision Blizzard Inc']");

	@FindBy(xpath="//h2[text()='Activision Blizzard Inc']") 
	WebElement activisionBanner;
	
	By activisionBannerBy = By.xpath("//h2[text()='Activision Blizzard Inc']");
	
	GenericUtils genUtils = new GenericUtils();
	private WebDriver driver=null;

	public VerifyCompanyVoteCardPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}


	public void checkCountryFilterExist()throws Exception{

		genUtils.checkFieldExist(searchCountryFilterBy, true, "Country Filter Search");
	}

	public void clickCountryFilterResetButton()throws Exception{

		genUtils.click(searchCountryFilterReset, "Country Reset button");
	}
	
	public void clickItemsPerPageArrow()throws Exception{

		genUtils.click(itemsPerPageArrow, "Items per page arrow icon");
	}
	
	public void clickOnMaximumNumber()throws Exception{

		genUtils.click(itemsPerPage, "Items per page maximum number");
	}
	
	
	public void checkCompanyNameActivision()throws Exception{

		genUtils.checkFieldExist(activisionLinkBy, true, "Company Name Activision");
	}
	
	public void clickOnCompanyNameActivision()throws Exception{

		genUtils.click(activisionLink, "Activision Blizzard Inc");
	}
	
	
	public void checkCompanyNameBanner()throws Exception{

		genUtils.checkFieldExist(activisionLinkBy, true, "Company Name Activision");
	}
}
