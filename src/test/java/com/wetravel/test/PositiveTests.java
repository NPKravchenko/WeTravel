package com.wetravel.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.internal.collections.Pair;

import com.google.common.base.Function;
import com.wetravel.base.BaseTest;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;



final class DateRangePicker {
	
	public DateRangePicker(WebDriver driver) {
		_driver = driver;
		hashMap.put("January", 1);
		hashMap.put("February", 2);
		hashMap.put("March", 3);
		hashMap.put("April", 4);
		hashMap.put("May", 5);
		hashMap.put("June", 6);
		hashMap.put("July", 7);
		hashMap.put("August", 8);
		hashMap.put("September", 9);
		hashMap.put("October", 10);
		hashMap.put("November", 11);
		hashMap.put("December", 12);
		
		_startDateBtn = _driver.findElement(By.id("startDate"));
		_endDateBtn   = _driver.findElement(By.id("endDate"));
		_startDateBtn.click();
		
		_nextBtn = _driver.findElement(By.xpath("//*[@id='tripbuilder']/div/div[2]/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[1]/button[2]"));
		_prevBtn = _driver.findElement(By.xpath("//*[@id=\'tripbuilder\']/div/div[2]/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[1]/button[1]"));
	
		_firstMonthElement  = _driver.findElement(By.xpath(_firstMonthXPath));
		//_secondMonthElement = _driver.findElement(By.xpath("//*[@id=\'tripbuilder\']/div/div[2]/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[2]/div/div[3]/table/caption/strong"));
	}
	
	public boolean ensureStartDate(int day, int month, int year) {
		
		//_startDateBtn = _driver.findElement(By.id("startDate"));
		_startDateBtn.click();
		
		if (!ensureMonth(month, year)) {
			return false;
		}
		
		if (!ensureDay(day)) {
			return false;
		}
		
		return true;
	}
	
	public boolean ensureEndDate(int day, int month, int year) {
		
		//_endDateBtn = _driver.findElement(By.id("endDate"));
		_endDateBtn.click();
		
		if (!ensureMonth(month, year)) {
			return false;
		}
		
		if (!ensureDay(day)) {
			return false;
		}
		
		return true;
	}
	
	public Date startDate() {
		
		String text = _startDateBtn.getAttribute("value").toString();
		
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public Date endDate() {
		
		String text = _endDateBtn.getAttribute("value").toString();
		
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	 
	
	private boolean ensureDay(int day) {
		
		WebElement dateWidgetFrom = _driver.findElement(By.xpath("//*[@id='tripbuilder']/div/div[2]/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[2]/div/div[2]/table/tbody"));
		
		List<WebElement> columns = dateWidgetFrom.findElements(By.tagName("td"));
		for (WebElement cell: columns) {
			
			if (!cell.isEnabled())
				continue;
			
			String text = cell.getText();
			if (text.isEmpty())
				continue;
			
			int cellDay = Integer.parseInt(text);
			if (cellDay != day)
				continue;
			cell.click();
			
			try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			return true;
		}
		
		return false;
	}
	
	private boolean ensureMonth(int month, int year) {
		
		_firstMonthElement = findFirstMonth("");
		String str = _firstMonthElement.getText();
		int[] monthYear = {0, 0};

		parseMonthString(str, monthYear);
		
		int currentMonth = monthYear[0];
		int currentYear  = monthYear[1];
		
		while (currentYear < year) {
			//click next button
			if (!_nextBtn.isEnabled())
				return false;
			_nextBtn.click();
			
			_firstMonthElement = findFirstMonth(str);
			str = _firstMonthElement.getText();
			parseMonthString(str, monthYear);
			currentMonth = monthYear[0];
			currentYear  = monthYear[1];
		}
		
		while (currentYear > year) {
			//click prev button
			if (!_prevBtn.isEnabled())
				return false;
			_prevBtn.click();
			_firstMonthElement = findFirstMonth(str);
			str = _firstMonthElement.getText();
			parseMonthString(str, monthYear);
			currentMonth = monthYear[0];
			currentYear  = monthYear[1];
		}
		
		Assert.assertEquals(currentYear, year);
		while (currentMonth < month) {
			//click next button
			if (!_nextBtn.isEnabled())
				return false;
			_nextBtn.click();
			_firstMonthElement = findFirstMonth(str);
			str = _firstMonthElement.getText();
			parseMonthString(str, monthYear);
			currentMonth = monthYear[0];
			currentYear  = monthYear[1];
		}
		
		while (currentMonth > month) {
			//click prev button
			if (!_prevBtn.isEnabled())
				return false;
			_prevBtn.click();
			_firstMonthElement = findFirstMonth(str);
			str = _firstMonthElement.getText();
			parseMonthString(str, monthYear);
			currentMonth = monthYear[0];
			currentYear  = monthYear[1];
		}
		
		Assert.assertEquals(currentYear, year);
		Assert.assertEquals(currentMonth, month);
		
		return true;
	}
	
private void parseMonthString(String string, int[] monthAndYear) {
	
	Assert.assertFalse(string.isEmpty());
	
	String[] monthYear = string.split("\\s+");
	Assert.assertTrue(monthYear.length == 2, "Expected Month and Year in" + string);
	Assert.assertTrue(hashMap.containsKey(monthYear[0]));
	
	monthAndYear[0] = hashMap.get(monthYear[0]);
	Assert.assertTrue(monthAndYear[0] != 0, "Wrong month index");
	
	monthAndYear[1] = Integer.parseInt(monthYear[1]);
}
	
private WebElement findFirstMonth(String prevText) {
	
	Wait<Pair<WebDriver, String>> wait = new FluentWait<Pair<WebDriver, String>>(new Pair<WebDriver, String>(_driver, prevText))
			.withTimeout(Duration.ofSeconds(20)) 			
			.pollingEvery(Duration.ofMillis(500)) 			
			.ignoring(NoSuchElementException.class);
	
    wait.until(new Function<Pair<WebDriver, String>, Boolean>(){
		
		public Boolean apply(Pair<WebDriver, String> pair ) {
			String text = pair.first().findElement(By.xpath(_firstMonthXPath)).getText();
			System.out.println("Prev: "+ pair.second() +" now: " + text);
			boolean res = !text.isEmpty() && 0!=text.compareTo(pair.second());
			System.out.println("Res: "+ String.valueOf(res));
			return res;
		}
	});
    
    return _driver.findElement(By.xpath(_firstMonthXPath));
}


private
	WebDriver _driver;
	WebElement _startDateBtn;
	WebElement _endDateBtn;
	WebElement _nextBtn;
	WebElement _prevBtn;
	WebElement _firstMonthElement;
	//WebElement _secondMonthElement;
	static final String _firstMonthXPath = "//*[@id='tripbuilder']/div/div[2]/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[2]/div/div[2]/table/caption";
	Map<String, Integer> hashMap = new HashMap<String, Integer>();
}

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

		// get today's date
		
		//WebElement dateRangePicker = driver.findElement(By.xpath("//*[@id=\"startDate\"]"));
		//dateRangePicker.sendKeys("03252020");
		//dateRangePicker.sendKeys(Keys.TAB);

		
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
		return;
		
		/*
		// click and open the datepicker
		WebElement DateRangePicker = driver.findElement(By.id("startDate"));
		DateRangePicker.click();
		
		WebElement month = driver.findElement(By.xpath("//*[@id='tripbuilder']/div/div[2]/div[1]/div/div/div/div/div[2]/div/div/div[2]/div[2]/div/div[2]/table/caption"));
		System.out.println(month.getText());
		

		// from date picker table
		WebElement startDate = driver.findElement(By.cssSelector(
				"div:nth-of-type(2) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(4) > .CalendarDay__button"));
		startDate.click();

		WebElement endDate = driver.findElement(By.cssSelector(
				"div:nth-of-type(2) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(5) > .CalendarDay__button"));
		endDate.click();

		WebElement nextButton = driver.findElement(By.xpath("//a[@id='next_step']"));
		nextButton.click();

		String expectedURL = "https://www.wetravel.com/itinerary_builder/new#/trip-privacy";
		String actualURL = driver.getCurrentUrl();
		Assert.assertTrue(actualURL.contains(expectedURL),
				"Actual URL does not correspond to expected URL.\nActual URL: " + actualURL + "\nExpected Message: "
						+ expectedURL);
						*/
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
