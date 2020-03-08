package com.wetravel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class TripPrivacyPage extends BasePageObject {
	
	private String pageURL = "https://www.wetravel.com/itinerary_builder/new#/trip-privacy";
	private By privateLinkLocator = By.xpath("//div[@class='trip-privacy']/label[1]");
	private By nextButtonLocator = By.id("next_step");
	private By weTravelLogoLocator = By.xpath("//img[@alt='wetravel logo']");
	private By popUpWindowLocator = By.xpath("//i[@class='wt-modal__close']");
	private By privateLabelActive = By.xpath("//label[@class='wt-text trip-privacy__label trip-privacy__label--active']");
	
	public TripPrivacyPage(WebDriver driver) {
		super(driver);
	}

	public String getPageURL() {
		return pageURL;
	}
	
	public void clickOnPrivateLink() {
		System.out.println("Clicking on Private link on Trip Privacy page");		
		click(privateLinkLocator);
		if (isElementVisible(privateLabelActive))
			System.out.println("Private link is active");
		else
			System.out.println("Private link is not active");
		
	}
	
	public void clickOnNextButton() {
		System.out.println("Clicking Next on Trip Privacy page");
		click(nextButtonLocator);
	}
	
	public void closeModalWelcome() {
		System.out.println("Clicking Next on Trip Privacy page");
		click(popUpWindowLocator);
	}
	
	// Verification if logOutButton is visible on the page
	public boolean isElementVisible(By locator) {
			return find(locator).isDisplayed();
		}
	
	public void verifyTravelLogo() {
		System.out.println("Verifying WeTravel logo on Trip Privacy page");
		if (isElementVisible(weTravelLogoLocator))
			System.out.println("WeTravel logo is presenred");
		else
			System.out.println("WeTravel logo is not presenred");
	}
}
