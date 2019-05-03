># **KarFrame** : 
>##### A Hybrid (Data + Keyword driven) Automation Framework    

### New Features!

Some of the Basic Functions Integerated in KarFrame ,
 
 - WebDriver - Properties - Logs - Excel - ReportNG - ExtentReports - Mail
Jenkins - Listeners - Responsive view Emulator - Different Web
browsers


### Driver Initialization : 
```sh
openBrowser  ( BrowserType.CHROME , "http://www.google.com" , false );

openBrowser ( BrowserType.RESPONSIVE , DeviceName.IPHONE_6_7_8 , "https://www.google.com", false );
```
| Arguments | Functions |
| ------ | ------ |
      |1st args |(Select BrowserType as (CHROME,FIREFOX,HEADLESS,IE,OPERA,RESPONSIVE)
      |2nd args |(Incase of Responsive ,select the deviceName forMobile Emulation)
      |3rd args |(Enter the URL for redirection)
      |4th args |(EnableVideo Recording True/False)


### Click Operation : click(By locator)
```sh
click(By.xpath("element"));
                   (Perform the Click event using the Element)
```
### Type Operation : type(By locator, String value /  Keys value)
```sh
type(By.xpath("element"),"Value");
                   (Perform the Type event using the Element and type the value)
```
```sh
type(By.xpath("element"),Keys.DELETE);
                   (Perform the Type event using the Element and Key Strokes)
```
### Drop-down select Operation : dropdownSelect(By locator, String value / int index )
```sh
dropdownSelect( By.linkText("element") , "Value" );
                     (Select the Dropdown with the drop-down value)
```
```sh
dropdownSelect( By.linkText("element") , index );
                     (Select the Dropdown with the drop-down index)
```

### Element Present/Absent : isElementPresent(By by)
```sh
isElementPresent( By.linkText("element")
                     (Returns true when element exists and returns false when element absents)
```

### Assert Verification : verifyEquals(String expected, String actual)
```sh
verifyEquals("expected","actual")
                     (Returns true when expected and actual strings are equal otherwise Throws error)
```

### keyboard Action : keyboardAction(int KeyEvent1, int KeyEvent2, int KeyEvent3)
```sh
keyboardAction(KeyEvent.VK_F1);
keyboardAction(KeyEvent.VK_F1 , KeyEvent.VK_F2);
keyboardAction(KeyEvent.VK_F1 , KeyEvent.VK_F2 , KeyEvent.VK_F3);
                        (Performs the Keystroke Operations)
```

### Mouse Hover Click Operation : mouseHoverClick(By destinationBy)
```sh
mouseHoverClick(By.xpath("element"))
                     (Performs Mouse Hover click operation with the elements)
```
### Double Click Operation : doubleClick(By destinationBy)
```sh
doubleClick(By.xpath("element"))
                     (Performs double click operation with the elements)
```

### Page Scroll Operation : scroll(int getValue / By destinationBy)
```sh
scroll(250);
                     (Scroll the page y axis into 250 pixels)
```
```sh
scroll(By.xpath("element"));
                     (Scroll the page in to view of element)
```

### Wait Operation : wait( int Seconds / By waitForElementBy )
```sh
wait(t);
                     (Wait for t Seconds)
```
```sh
wait(By.xpath("element"));
                     (Wait for the element to be Present)
```

### Run Mode Operation : runMode(Hashtable<String, String> data)
#### Note :

   1.Test class Name must be same as in the Excel Sheet Name,
   
   2.Execute runmode data only in the Excel Sheet (Test Class Name),
   
   3.Not consider the Overall TestSuite Sheet
   
        
```sh
  @Test(dataProviderClass = Utilities.class,dataProvider = "dp") -setup
  runMode(data);
    *
    *
    data.get(""columnName"") - to get the strings
                (Run the Test cases if the Run Mode is Y otherwise Skip the Test)
```

### Run Mode Operation : runMode(String ExcelSheetName)
#### Note :

   1.Test class Name must be same as in the Excel Sheet Name
   
   2.consider only the Overall TestSuite Sheet (Test_suite)
   
   3.Select the Given Excel Sheet Name
        
```sh
  @Test() 
    *
  runMode();
    *
    *
               (Run the Test cases if the Run Mode is Y otherwise Skip the Test)
```

### Run Mode Operation : runMode();
#### Note :

   1.Test class Name must be same as in the Excel Sheet Name
   
   2.consider only the Overall TestSuite Sheet (Test_suite)
   
   3.Default select the Current Class Name as in Excel Sheet
          
```sh
  @Test() 
    *
  runMode();
    *
    *
               (Run the Test cases if the Run Mode is Y otherwise Skip the Test)
```
>### UTILITIES :
### Capture Screenshot :

```sh
captureScreenshot(ITestResult result)
                    (Capture Screenshot Using ITest Result)
```
 - It captures the Screenshot Image ,Encode and Save the file in Utilities.`screenshotPath` 
 - Returns Encoded File path
```sh
customCaptureScreenshot()
                    (Only take screenshot and return path without Using ITest Result)
```
 - This Method Returns the Absolute Image path of the Screenshot
```sh
customScreenShotWithReport() 
                    (Capture Screenshot and attach to report without Using ITest Result)
```
 - It capture the ScreenShot and Attach Image in the Extent Report
```sh
customScreenShotWithZip() 
                    (Capture Screenshot and attach to report without Using ITest Result and make it a Zip file.)
```
- It capture the ScreenShot ,make Zip and Attach Zip file in the Extent Report

### Video Recording :

```sh
openBrowser(BrowserType type , String URL ,boolean VideoRecording)-syntax
*
*
openBrowser(BrowserType.CHROME,"https://www.google.com",true);
```


### Encryption :

```sh
encrypt(String PasswordString)  -syntax
                (Returns the Encrypted String)
```

### Decryption :

```sh
decrypt(String encryptedString) -syntax
                (Returns the Decryption String)
```
### Read data from Excel:

```sh
@Test(dataProviderClass = Utilities.class,dataProvider = "dp") -setup
*
*
data.get(""columnName"") - to get the strings
```

