package com.wetravel.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.wetravel.base.BaseTest;
import com.wetravel.pages.ItineraryBuilderPage;
import com.wetravel.pages.StartPage;
import com.wetravel.pages.TripDatesPage;
import com.wetravel.pages.TripPrivacyPage;

public class PositiveTests extends BaseTest {
	
	@Test(priority = 1)
	@Parameters({ "destination","startDate","endDate" })
	public void positiveTest(String destination,String startDate, String endDate) {
		
		StartPage startPage = new StartPage(driver);
		startPage.OpenStartPage();
		startPage.clickTryForFreeButton();
		
		ItineraryBuilderPage itineraryBuilderPage = new ItineraryBuilderPage(driver);
		
		Assert.assertTrue(itineraryBuilderPage.getPageURL().contains(itineraryBuilderPage.getCurrentURL()),
				"Actual URL does not correspond to expected URL.\nActual URL: " + itineraryBuilderPage.getPageURL() + "\nExpected Message: "
						+ itineraryBuilderPage.getCurrentURL());

		itineraryBuilderPage.enterDestination(destination);
		itineraryBuilderPage.clickOnNextButton();
				
		TripDatesPage tripDatesPage = new TripDatesPage(driver);
		Assert.assertTrue(tripDatesPage.getPageURL().contains(tripDatesPage.getCurrentURL()),
				"Actual URL does not correspond to expected URL.\nActual URL: " + tripDatesPage.getPageURL() + "\nExpected Message: "
						+ tripDatesPage.getCurrentURL());
		
		tripDatesPage.enterTripDates(startDate, endDate);
			
		TripPrivacyPage tripPrivacyPage = new TripPrivacyPage(driver);
		Assert.assertTrue(tripPrivacyPage.getPageURL().contains(tripPrivacyPage.getCurrentURL()),
				"Actual URL does not correspond to expected URL.\nActual URL: " + tripPrivacyPage.getPageURL() + "\nExpected Message: "
						+ tripPrivacyPage.getCurrentURL());
		tripPrivacyPage.clickOnPrivateLink();
		tripPrivacyPage.clickOnNextButton();
		tripPrivacyPage.verifyTravelLogo();	
	}

}
