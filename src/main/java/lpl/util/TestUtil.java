package lpl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import lpl.base.TestBase;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static String ResultsFolderPath = "Results";
	public static String TESTDATA_SHEET_PATH = "src\\main\\java\\lpl\\testdata\\LplTestData.xlsx";
	public static String CurrentDateAndTime;
	static Workbook book;
	static Sheet sheet;
	
	
	public void switchToFrame(){
		driver.switchTo().frame("mainpanel");
	}
	
	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				// System.out.println(data[i][k]);
			}
		}
		return data;
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		
		}
	

	public static String createresultsfolder() //line1078
	{
		DateFormat dateTimelnstance = SimpleDateFormat.getDateTimeInstance();
		String DateTimeStamp = dateTimelnstance.format(Calendar.getInstance().getTime()) ; 
		DateTimeStamp = DateTimeStamp.replace(",","");
		DateTimeStamp = DateTimeStamp.replace(" ","_");
		DateTimeStamp = DateTimeStamp.replace(":","-");
		ResultsFolderPath = System.getProperty( "user.dir") + "\\" + ResultsFolderPath; 
		File dir = new File((ResultsFolderPath)+"\\" + DateTimeStamp); 
		dir.mkdir();
	//	File dir2 = new File(ResultsFolderPath + "\\" + DateTimeStamp + "\\Screenshots");
	//	dir2.mkdir();
		CurrentDateAndTime = DateTimeStamp;
		return ResultsFolderPath + "\\" + DateTimeStamp;
	}

}
