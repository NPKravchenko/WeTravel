package com.wetravel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {
	
	protected WebDriver driver;
	
	public BasePageObject (WebDriver driver) {
		this.driver = driver;
	}
	
// open URL
	protected void StartPage(String url) {
		driver.get(url);
	}
	
	//find element
	protected WebElement find(By locator) {
		return driver.findElement(locator);	
	}
	
	protected void WaitForVisibility(By locator, Integer timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected void enterText(String text, By locator) {
		WaitForVisibility(locator, 10);
		find(locator).sendKeys(text);
	}
	
	protected void click(By locator) {
		WaitForVisibility(locator, 10);
		find(locator).click();
	}
	
	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}
	
}
