package com.wetravel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StartPage extends BasePageObject{
	
	private String URL = "https://www.wetravel.com/";
	private By TryForFreeLocator = 	By.xpath("//a[@class='wt-button  wt-button--blue wt-button--md  landing-banner__button']");
		
	public StartPage(WebDriver driver) {
		super(driver);
	}
	
	public void OpenStartPage() {
		System.out.println("Opening WeTravel");
		StartPage(URL);
		System.out.println("WeTravel is opened");
	}

	public void clickTryForFreeButton() {
		System.out.println("Opening Try For Free Page .");
		click(TryForFreeLocator);
		System.out.println("Try For Free Page is opened.");
	}
	
			
}
