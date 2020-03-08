package com.wetravel.test;

import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wetravel.base.BaseTest;
import com.wetravel.base.DateRangePicker;


public class PositiveTests extends BaseTest {
	
	private void openTryForFreePage() {

		System.out.println("Opening Try For Free page");

//		open test page
		String url = "https://www.wetravel.com/";
		driver.get(url);

//		click Try For Free button
		WebElement tryButton = driver.findElement(
				By.xpath("//a[@class='wt-button  wt-button--blue wt-button--md  landing-banner__button']"));
		tryButton.click();

		String expectedURL = "https://www.wetravel.com/itinerary_builder/new";
		String actualURL = driver.getCurrentUrl();
		Assert.assertTrue(actualURL.contains(expectedURL),
				"Actual URL does not correspond to expected URL.\nActual URL: " + actualURL + "\nExpected Message: "
						+ expectedURL);

		System.out.println("Try For Free Page is opened.");

	}

	private void Destination(String destination) {

		System.out.println("Filling in Destination");

		WebDriverWait wait = new WebDriverWait(driver, 10);

		WebElement destinationElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("location")));

		destinationElement.sendKeys(destination);

		WebElement nextButton = driver.findElement(By.xpath("//a[@id='next_step']"));
		nextButton.click();

		String expectedURL = "https://www.wetravel.com/itinerary_builder/new#/trip-dates";
		String actualURL = driver.getCurrentUrl();
		Assert.assertTrue(actualURL.contains(expectedURL),
				"Actual URL does not correspond to expected URL.\nActual URL: " + actualURL + "\nExpected Message: "
						+ expectedURL);
	}

	private void Dates() {

		System.out.println("Filling in Start Date and End Date");

		DateRangePicker dataPicker = new DateRangePicker(driver);
		Assert.assertTrue(dataPicker.ensureStartDate(22, 03, 2020));
		Assert.assertTrue(dataPicker.ensureEndDate(22, 12, 2020));
		
		Date startDate = dataPicker.startDate();
		Date endDate   = dataPicker.endDate();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		Assert.assertEquals(day, 22);
		Assert.assertEquals(month, 3);
		Assert.assertEquals(year, 2020);
		
		cal.setTime(endDate);
		
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH)+1;
		year = cal.get(Calendar.YEAR);
		Assert.assertEquals(day, 22);
		Assert.assertEquals(month, 12);
		Assert.assertEquals(year, 2020);
		
		
		WebElement nextButton = driver.findElement(By.xpath("//a[@id='next_step']"));
		nextButton.click();
		
		String expectedURL = "https://www.wetravel.com/itinerary_builder/new#/trip-privacy";
		String actualURL = driver.getCurrentUrl();
		Assert.assertTrue(actualURL.contains(expectedURL),
				"Actual URL does not correspond to expected URL.\nActual URL: " + actualURL + "\nExpected Message: "
						+ expectedURL);
	}

	private void clickPrivateLink() {

		System.out.println("Clicking Private link");

		WebElement privateLink = driver.findElement(By.xpath("//div[@class='trip-privacy']/label[1]"));
		privateLink.click();
		privateLink.isSelected();
		
		sleep(3000);
		
		String PrivateSelected = privateLink.getCssValue("border-color");
		System.out.println("actual private border color is: " + PrivateSelected);
		
		
		WebElement publicLink = driver.findElement(By.xpath("//div[@class='trip-privacy']/label[2]"));
		String PublicSelected = publicLink.getCssValue("border-color");
		System.out.println("actual public border color is: " + PublicSelected);
		
		String  borderColor = "rgb(25, 190, 211)";
		System.out.println("expected border color is: " + borderColor);
		
		if (PrivateSelected == PublicSelected)
			System.out.println("Private block not is clicked");
		else
			System.out.println("Private block is clicked");

	}

	
	private void clickNextOnTripPrivacy() {

		System.out.println("Clicking on Next on Trip Privacy");

		WebElement nextButton = driver.findElement(By.id("next_step"));
		nextButton.click();
	}

	private void closeWelcome() {

		System.out.println("Closing Welcome pop up");

		WebDriverWait wait = new WebDriverWait(driver, 10);

		WebElement popUpWindow = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='wt-modal__close']")));
		popUpWindow.click();

	}

	private void verifyTravelLogo() {

		System.out.println("Verify Travel logo");

		WebElement WeTravelLogo = driver.findElement(By.xpath("//img[@alt='wetravel logo']"));
		boolean logoPresent = WeTravelLogo.isDisplayed();
		if (!logoPresent)
			System.out.println("WeTravel logo is not presenred");
		else
			System.out.println("WeTravel logo is presenred");
	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveTest() {

		openTryForFreePage();
		Destination("San Francisco");
		Dates();
		clickPrivateLink();
		clickNextOnTripPrivacy();
		closeWelcome();
		verifyTravelLogo();

	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
