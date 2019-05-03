package framework.utilities;

import framework.base.KarFrame;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class Utilities extends KarFrame {

    public static String screenshotPath;
    public static String screenshotName;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd SSS");

    public static void captureScreenshot(ITestResult result) throws IOException {

        //TODO captureScreenshot Using ITest Result

        Date date = new Date();
        screenshotName = result.getName() + "_" + dateFormat.format(date);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
            screenshotPath = "data:image/png;base64," + encodedBase64;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String dest = System.getProperty("user.dir") + "/target/Test-ScreenShots/" + screenshotName + ".png";
        FileUtils.copyFile(scrFile, new File(dest), true);
    }

    public static String customCaptureScreenshot() throws IOException {

        //TODO customCaptureScreenshot only take screenshot and return absolute path without Using ITest Result


        Date date = new Date();
        screenshotName = driver.getTitle() + "_" + dateFormat.format(date);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
            screenshotPath = "data:image/png;base64," + encodedBase64;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String dest = System.getProperty("user.dir") + "/target/Test-ScreenShots/" + screenshotName + ".png";
        FileUtils.copyFile(scrFile, new File(dest), true);
        return dest;
    }

    public static void customScreenShotWithReport() throws IOException {

        //TODO customScreenShotWithReport without Using ITest Result

        Date date = new Date();
        screenshotName = driver.getTitle() + "_" + dateFormat.format(date);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
            screenshotPath = "data:image/png;base64," + encodedBase64;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String dest = System.getProperty("user.dir") + "/target/Test-ScreenShots/" + screenshotName + ".png";
        FileUtils.copyFile(scrFile, new File(dest), true);

        test.info("Screenshot Attached").addScreenCaptureFromPath(screenshotPath, "Screenshot Attached");
        log.info("Screenshot Attached");
    }

    public static void customScreenShotWithZipReport() throws IOException {

        //TODO custom ScreenShot With Zip Report without Using ITest Result

        Date date = new Date();
        ReportZip();
        screenshotName = driver.getTitle() + "_" + dateFormat.format(date);

        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            fileInputStreamReader = new FileInputStream(scrFile);
            byte[] bytes = new byte[(int) scrFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
            screenshotPath = "data:image/png;base64," + encodedBase64;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String dest = System.getProperty("user.dir") + "/target/Test-ScreenShots/" + screenshotName + ".png";
        FileUtils.copyFile(scrFile, new File(dest), true);

        test.info("Screenshot Attached").addScreenCaptureFromPath(screenshotPath, "Screenshot Attached");
        log.info("Screenshot Attached");
    }

    public static void ReportZip() {

        //TODO internal method for Zip

        try {
            FileOutputStream fos = new FileOutputStream("KarZipReports.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            String file1Name = "Karframe.html";
			/*String file2Name = "file2.txt";
			String file3Name = "folder/file3.txt";
			String file4Name = "folder/file4.txt";
			String file5Name = "f1/f2/f3/file5.txt";*/

            addToZipFile(file1Name, zos);
			/*addToZipFile(file2Name, zos);
			addToZipFile(file3Name, zos);
			addToZipFile(file4Name, zos);
			addToZipFile(file5Name, zos);*/

            zos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

        //TODO internal method for Zip Name

        System.out.println("Writing '" + fileName + "' to zip file");
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";

    public static String encrypt(String Password) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(Password.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static ScreenRecorder screenRecorder;

    public static void startRecording() throws Exception {

        // set the graphics configuration
        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        // initialize the screen recorder:
        // - default graphics configuration
        // - full screen recording
        // - record in AVI format
        // - 15 frames per second
        // - black mouse pointer
        // - no audio
        // - save capture to predefined location


        screenRecorder = new ScreenRecorder(gc,
                gc.getBounds(),
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                null,
                new File(System.getProperty("user.dir") + "/target/Recorded-Video"));
        screenRecorder.start();
    }


    public static void stopRecording() throws Exception {
        try {
            Utilities.screenRecorder.stop();
        } catch (Exception e) {
        }
    }


    @DataProvider(name = "dp")
    public Object[][] getData(Method m) {

        String sheetName = m.getName();
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][1];

        Hashtable<String, String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

            table = new Hashtable<String, String>();

            for (int colNum = 0; colNum < cols; colNum++) {

                // data[0][0]
                table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
                data[rowNum - 2][0] = table;
            }

        }

        return data;

    }


    public static boolean isTestRunnable(String testName, ExcelReader excel) {

        String sheetName = "test_suite"; //default testSuite
        int rows = excel.getRowCount(sheetName);


        for (int rNum = 2; rNum <= rows; rNum++) {

            String testCase = excel.getCellData(sheetName, "TCID", rNum);

            if (testCase.equalsIgnoreCase(testName)) {

                String runmode = excel.getCellData(sheetName, "Runmode", rNum);

                if (runmode.equalsIgnoreCase("Y"))
                    return true;
                else
                    return false;
            }

        }
        return false;
    }


}
