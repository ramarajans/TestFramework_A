package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import driverManager.DriverManager;
import utils.GenericUtils;

public class VerifyCompanyVoteCardPage {


	@FindBy(id="txt-multiselect-static-search-CountryFilter") 
	WebElement searchCountryFilter;

	By searchCountryFilterBy = By.id("txt-multiselect-static-search-CountryFilter");

	@FindBy(xpath="//div[@id='filter-country']//button[@id='btn-close']") 
	WebElement searchCountryFilterReset;


	@FindBy(xpath="//fieldset[@class='fieldset-county']//following-sibling::div//button[@id='btn-close']") 
	List<WebElement> searchCountryFilterResetButtons;

	@FindBy(xpath="//span[@class='k-dropdown-wrap k-state-default']/span[@class='k-select']")
	public static WebElement itemsPerPageArrow;

	@FindBy(xpath="//a[@title='Go to the next page']") 
	WebElement gotoNextPage;

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

	public boolean checkCompanyNameActivision()throws Exception{

		boolean isFieldExist = false;
		
		do {
			if(!genUtils.checkFieldExist(activisionLinkBy, "Company Name Activision"))
				genUtils.actionsClick(gotoNextPage,"Go to next page");
			else {
				isFieldExist = true;
				break;
			}

		}while(gotoNextPage.isEnabled());

		return isFieldExist;
	}

	public void clickOnCompanyNameActivision()throws Exception{

		genUtils.click(activisionLink, "Activision Blizzard Inc");
	}


	public void checkCompanyNameBanner()throws Exception{

		genUtils.checkFieldExist(activisionBannerBy, true, "Company Name Activision");
	}


	public void clickCountryFilterResetButtons()throws Exception{

		System.out.println("searchCountryFilterResetButtons size : "+searchCountryFilterResetButtons.size());
		for(WebElement ele:searchCountryFilterResetButtons) {
			genUtils.pageScroll(ele);
			genUtils.click(ele, "Reset button");
		}
	}
}
