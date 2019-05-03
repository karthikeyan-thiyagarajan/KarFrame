package com.kar.framework.testcases;

import framework.base.KarFrame;
import framework.utilities.BrowserType;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends KarFrame {

	
	@Test
	public void bankManagerLoginTest() throws Exception {
		

		openBrowser(BrowserType.CHROME,"http://www.way2automation.com/angularjs-protractor/banking/#/login",false);
		verifyEquals("abc", "xyz");
		Thread.sleep(3000);
		log.debug("Inside Login Test");
		//click("bmlBtn_CSS");

		//Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),"Login not successful");
		
		log.debug("Login successfully executed");
	}
}
