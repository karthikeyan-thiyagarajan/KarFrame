package com.kar.framework.testcases;

import framework.base.KarFrame;
import framework.utilities.Utilities;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class OpenAccountTest extends KarFrame {

	@Test()
	public void openAccountTest() throws InterruptedException {

		//runMode("Sample");
		runMode("Sample");
		/*Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();*/

	}
}
