package com.wetravel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ItineraryBuilderPage extends BasePageObject {
	
	private String pageURL = "https://www.wetravel.com/itinerary_builder/new#/";
	private By DestinationLocator = By.id("location");
	private By NextButtonLocator = By.xpath("//a[@id='next_step']");
		
	public ItineraryBuilderPage (WebDriver driver) {
		super(driver);
	}
	
	public String getPageURL() {
		return pageURL;
	}

	public void enterDestination(String destination) {
		System.out.println("Filling in Destination: " + destination);
		enterText(destination, DestinationLocator);
	}
	
	public void clickOnNextButton() {
		System.out.println("Clicking Next on Itinerary Builder Page");
		click(NextButtonLocator);
	}
}
