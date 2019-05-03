package com.kar.framework.testcases;

import framework.base.KarFrame;
import framework.listeners.CustomListeners;
import framework.utilities.BrowserType;
import framework.utilities.DeviceName;
import framework.utilities.Utilities;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;



public class Sample extends KarFrame{
    @Test(dataProviderClass = Utilities.class,dataProvider = "dp")
    public void sample(Hashtable<String,String> data) throws Exception {

       /* runMode(data);
        System.out.println(data.get("TCID"));*/

        //openBrowser(BrowserType.RESPONSIVE ,DeviceName.IPHONE_6_7_8,"https://www.google.com", false);
        /*System.out.println(Utilities.customCaptureScreenshot());
        System.out.println(Utilities.screenshotPath);
        System.out.println(Utilities.screenshotName);*/
     /*   System.out.println(Utilities.encrypt("email-smtp.us-east-1.amazonaws.com"));
        System.out.println(Utilities.decrypt("6nH1eslKRIb3tvGyivJu9h7U/knT0UzHcsWqVgIPngfRl3dLpQkeEO+Uvxw/B15K"));
*/

       // dropdownSelect(By.linkText("Gmail"),"sdjbd");
     // doubleClick(By.linkText("Gmail"));
     // mouseHoverClick(By.linkText("Gmail"));
       // Utilities.customScreenShotWithReport();
       // isElementPresent(By.linkText("Gmail"));

    }


}
