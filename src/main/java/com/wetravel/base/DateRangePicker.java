package com.wetravel.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.internal.collections.Pair;

import com.google.common.base.Function;

import java.util.Map;
import java.util.NoSuchElementException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DateRangePicker {
	
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
		
		_startDateBtn.click();
		
		if (!ensureMonth(month, year)) {
			return false;
		}
		
		if (!ensureDay(day)) {
			return false;
		}
		
		return true;
	}
	
	public boolean ensureStartDate(String textDate) {
		//expected date format is dd-mm-yyy
		
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(textDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		return ensureStartDate(day, month, year);	
	}
	
	public boolean ensureEndDate(String textDate) {
		//expected date format is dd-mm-yyy
		
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(textDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		return ensureEndDate(day, month, year);	
	}
	
	public boolean ensureEndDate(int day, int month, int year) {
		
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
			//System.out.println("Prev: "+ pair.second() +" now: " + text);
			boolean res = !text.isEmpty() && 0!=text.compareTo(pair.second());
			//System.out.println("Res: "+ String.valueOf(res));
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