package framework.listeners;

import com.aventstack.extentreports.Status;
import framework.base.KarFrame;
import framework.utilities.Utilities;
import org.testng.*;

import java.io.IOException;

public class CustomListeners extends KarFrame implements ITestListener, ISuiteListener {
    public static String methodName;
    public String messageBody;

    public void onFinish(ITestContext arg0) {

    }

    public void onStart(ITestContext arg0) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

    }

    public void onTestFailure(ITestResult arg0) {

        try {
            //TODO EXTENT REPORT
            Utilities.captureScreenshot(arg0);    //Takes ScreenShot
            test.log(Status.FAIL, arg0.getName().toUpperCase() + " Failed with exception : " + arg0.getThrowable());
            test.fail("Screen Shot :").addScreenCaptureFromPath(Utilities.screenshotPath);  //add the Path of the Screenshot to report

            //TODO REPORTNG REPORT
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            Reporter.log("Click to see Screenshot");
            Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + ">Screenshot</a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            Reporter.log("<a target=\"_blank\" href=" + Utilities.screenshotName + "><img src=" + Utilities.screenshotName + " height=200 width=200></img></a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult arg0) {

        log.debug(arg0.getName().toUpperCase() + "- Test is skipped");
        test.log(Status.SKIP, arg0.getName().toUpperCase() + "- Test is skipped");

    }


    public void onTestStart(ITestResult arg0) {
        methodName = arg0.getTestClass().getRealClass().getSimpleName();
        test = rep.createTest(arg0.getName().toUpperCase());

    }

    public void onTestSuccess(ITestResult arg0) {

        test.log(Status.PASS, arg0.getName().toUpperCase() + " PASS");
        log.info(arg0.getName().toUpperCase() + " PASS");


    }

    public void onFinish(ISuite arg0) {
        log.info("Test Finished");
        try {
            Utilities.stopRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rep.flush();

    }

    public void onStart(ISuite arg0) {

        log.info("Test Started");
    }
}

