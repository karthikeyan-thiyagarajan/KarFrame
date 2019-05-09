package framework.listeners;

import com.aventstack.extentreports.Status;
import framework.base.KarFrame;
import framework.utilities.Utilities;
import org.testng.*;

import java.io.IOException;

public class CustomListeners extends KarFrame implements ITestListener, ISuiteListener {
    public String messageBody;
    public static String methodName;

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
            //rep.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult arg0) {

        log.debug(arg0.getName().toUpperCase() + "- Test is skipped");
        test.log(Status.SKIP, arg0.getName().toUpperCase() + "- Test is skipped");

        //rep.flush();
    }


    public void onTestStart(ITestResult arg0) {
        methodName=arg0.getTestClass().getRealClass().getSimpleName();
        test = rep.createTest(arg0.getName().toUpperCase());

    }

    public void onTestSuccess(ITestResult arg0) {

        test.log(Status.PASS, arg0.getName().toUpperCase() + " PASS");
        log.info(arg0.getName().toUpperCase() + " PASS");
        //rep.flush();

    }

    public void onFinish(ISuite arg0) {
        log.info("Test Finished");
        try {
            Utilities.stopRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rep.flush();

		/*MonitoringMail mail = new MonitoringMail();
		 
		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenLiveProject/Extent_Reports/";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		*/
    }

    public void onStart(ISuite arg0) {

        log.info("Test Started");
    }
}
