package com.wetravel.pages;

import org.openqa.selenium.By;

import com.wetravel.base.DateRangePicker;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class TripDatesPage extends BasePageObject {
	
	private String pageURL = "https://www.wetravel.com/itinerary_builder/new#/trip-dates";
	private By dateRangePickerLocator = By.xpath("//*[@id=\"startDate\"]");
	private By StartDateLocator = By.cssSelector(
			"div:nth-of-type(2) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(4) > .CalendarDay__button");
	private By EndDateLocator = By.cssSelector(
			"div:nth-of-type(2) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(5) > .CalendarDay__button");
	private By NextButtonLocator = By.xpath("//a[@id='next_step']");
	
	public TripDatesPage (WebDriver driver) {
		super(driver);
	}
	
	public String getPageURL() {
		return pageURL;
	}

	public void enterTripDates(String startDate, String endDate) {
		System.out.println("Filling in Dates");
		
		DateRangePicker picker = new DateRangePicker(driver);
		Assert.assertTrue(picker.ensureStartDate(startDate));
		Assert.assertTrue(picker.ensureEndDate(endDate));
		
		click(dateRangePickerLocator);
		click(StartDateLocator);
		click(EndDateLocator);
		click(NextButtonLocator);
		System.out.println("Dates are added and Next is clicked");
	}
}
