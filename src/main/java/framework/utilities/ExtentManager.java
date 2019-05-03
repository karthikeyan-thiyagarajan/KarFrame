package framework.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance("Karframe-Report.html");

		return extent;
	}

	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Karframe Report");
		htmlReporter.config().setEncoding("UTF-8");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setReportName(fileName);
		htmlReporter.getConfigContext();
		htmlReporter.getTestList();
		htmlReporter.getSystemAttributeContext();
		htmlReporter.getCategoryContextInfo();

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		return extent;
	}
}
