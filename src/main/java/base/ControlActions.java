package base;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import customException.InvalidLocatorException;

public abstract class ControlActions {
	protected static WebDriver driver;
	protected static WebDriverWait wait;
	
	
	static public void start() {
		//WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://mpractice-trials79.orangehrmlive.com");
        wait = new WebDriverWait(driver, 30);
	}
	
	protected WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		locatorType = locatorType.toUpperCase();
		WebElement element = null;
		switch(locatorType) {
			case "XPATH" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				else
					element = driver.findElement(By.xpath(locatorValue));
				break;
			
			case "CSS" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
				else
					element = driver.findElement(By.cssSelector(locatorValue));
				break;
			
			case "ID" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				else
					element = driver.findElement(By.id(locatorValue));
				break;
			
			case "LINKTEXT" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
				else
					element = driver.findElement(By.linkText(locatorValue));
				break;
			
			
			case "PARTIALLINKTEXT" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
				else
					element = driver.findElement(By.partialLinkText(locatorValue));
				break;
			
			case "CLASSNAME" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
				else
					element = driver.findElement(By.className(locatorValue));
				break;
			
			case "TAGNAME" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
				else
					element = driver.findElement(By.tagName(locatorValue));
			break;
			
			case "NAME" : 
				if(isWaitRequired)
					element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
				else
					element = driver.findElement(By.name(locatorValue));
				break;
				
			default :
				throw new InvalidLocatorException("Locator type " + locatorType + " is invalid");
		}
		return element;
	}
	
	protected List<String> getListOfElementText(String locator){
		List<WebElement> listOfElements = driver.findElements(By.xpath(locator));
		List<String> listOfElementsText = new ArrayList<>();
		
		for(WebElement e : listOfElements) {
			listOfElementsText.add(e.getText());
		}
		return listOfElementsText;
	}
	
	protected List<String> getListOfElementText(List<WebElement> listOfElements){
		List<String> listOfElementsText = new ArrayList<>();
		
		for(WebElement e : listOfElements) {
			listOfElementsText.add(e.getText());
		}
		return listOfElementsText;
	}
	
	protected void mouseHover(String locatorType, String locatorValue) {
		WebElement e = getElement(locatorType, locatorValue, true);
		mouseHover(e,true);
	}
	
	protected void mouseHover(WebElement e, boolean isWaitRequired) {
		waitIfRequired(e, isWaitRequired);
		Actions action = new Actions(driver);
		action.moveToElement(e).build().perform();
	}
	
	protected WebElement waitForElementToBeVisible(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue, true);
	}
	
	protected void waitForElementToBeVisible(WebElement e) {
		waitIfRequired(e, true);
	}
	
	protected void waitForInvisibilyOfElement(WebElement e) {
		wait.until(ExpectedConditions.invisibilityOf(e));
	}
	
	protected void waitUntilTextToBePresent(WebElement e, String text) {
		wait.until(ExpectedConditions.textToBePresentInElement(e, text));
	}
	
	protected String getElementText(WebElement e, boolean isWaitRequired) {
		waitIfRequired(e, isWaitRequired);
		return e.getText();
	}
	
	protected String getElementText(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		return e.getText();
	}
	
	protected String getInputElementText(WebElement e) {
		waitIfRequired(e, true);
		return e.getAttribute("value");
	}
	
	protected String getInputElementText(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		return e.getAttribute("value");
	}
	
	protected boolean isElementDisplayed(String locatorType, String locatorValue, boolean isWaitRequired) {
		try {
			WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
			return e.isDisplayed();
		}catch(Exception ne) {
			return false;
		}
	}
	
	protected boolean isElementDisplayed(WebElement element, boolean isWaitRequired){
		try {
			waitIfRequired(element, isWaitRequired);
			return element.isDisplayed();
		}catch(TimeoutException e) {
			return false;
		}
	}
	
	protected void setText(String textToBeSent, WebElement element, boolean isWaitRequired) {
		waitIfRequired(element, isWaitRequired);
		element.sendKeys(textToBeSent);
	}
	
	private void waitIfRequired(WebElement element, boolean isWaitRequired) {
		if(isWaitRequired) {
			wait.until(ExpectedConditions.visibilityOf(element));
		}
	}
	
	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	protected void clickOnElement(WebElement e, boolean isWaitRequired) {
		waitIfRequired(e, isWaitRequired);
		e.click();
	}
	
	protected void waitUntilElementsToBeMoreThan(By by, int count) {
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by,count));
	}
	
	public static void closeBrowser() {
		driver.close();
	}
}
