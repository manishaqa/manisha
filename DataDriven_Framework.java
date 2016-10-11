import java.io.File;
import java.io.FileInputStream;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;




public class Facebook_DataDriven_Framework {
	
private WebDriver mydriver;
String myURL;
String MyFirstName;
String MyLastName;
String MyEmailID;
String MyReenterEmail;
String MyPassword;
String MyMonth;
String MyDay;
String MyYear;
String search_result;

int myRows,myCols;

	/**
	 * @param args
	 */
	//Beginning part
	
	@Before
	public void setup()throws Exception{
	
	
	File file = new File("C:\\Users\\manisha\\Downloads\\Scripts and Files\\Scripts and Files\\IEDriverServer_Win32_2.35.1\\IEDriverServer.exe");
	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	
	mydriver=new InternetExplorerDriver();
	myURL="http://www.facebook.com";
	
	
	}
	
	@Test
	public void mymaintest()throws Exception{
		
	String SheetPath="C:/Users/manisha/Desktop/Facebook_Framework_DataSheet.xls";
	
	String [][] myXLSheet=readXLSheet(SheetPath,"TestData");
	
	for (int k=1;k<myRows;k++){
		
 MyFirstName=myXLSheet[k][0];
 MyLastName=myXLSheet[k][1];
 MyEmailID=myXLSheet[k][2];
 MyReenterEmail=myXLSheet[k][2];
 MyPassword=myXLSheet[k][3];
 MyMonth=myXLSheet[k][4];
 MyDay=myXLSheet[k][5];
 MyYear=myXLSheet[k][6];
 
	mydriver.navigate().to(myURL);	
	
	mydriver.findElement(By.xpath(".//*[@id='u_0_0']")).sendKeys(MyFirstName);
	mydriver.findElement(By.xpath(".//*[@id='u_0_1']")).sendKeys(MyLastName);
	mydriver.findElement(By.xpath(".//*[@id='u_0_2']")).sendKeys(MyEmailID);
	mydriver.findElement(By.xpath(".//*[@id='u_0_3']")).sendKeys(MyReenterEmail);
	mydriver.findElement(By.xpath(".//*[@id='u_0_4']")).sendKeys(MyPassword);
	
	
	//Select Month from Dropdown
	Select month=new Select (mydriver.findElement(By.xpath(".//*[@id='month']")));
	
	month.selectByVisibleText(MyMonth);
	
	//Select Day Dropdown
    Select day=new Select (mydriver.findElement(By.xpath(".//*[@id='day']")));
	
	day.selectByVisibleText(MyDay);
	
    Select year=new Select (mydriver.findElement(By.xpath(".//*[@id='year']")));
	
	year.selectByVisibleText(MyYear);
	
	
	//Choose Male/Female radio button
	mydriver.findElement(By.xpath(".//*[@id='u_0_6']")).click();
	
	
	//Click on Signup
	mydriver.findElement(By.xpath(".//*[@id='u_0_7']")).click();
	
	
	search_result=mydriver.findElement(By.xpath("//div[@id='reg_error_inner']")).getText();
	
	System.out.println(search_result);
	
	
	if (search_result.equals("Please choose a more secure password. It should be longer than 6 characters, unique to you, and difficult for others to guess.")){
	System.out.println("Pass: error message exists on using unsecured and 6 characters long password");
	} else 
	System.out.println("Fail: error message does not exist on using unsecured password");
	
	
	}
	}
	
	
		// Method/Function for reading data from Excel Sheet
		public String[][] readXLSheet(String SheetPath, String SheetName) throws Exception{

			String[][] xData;                                                                
			
			File myXLSheet = new File(SheetPath);                                
			FileInputStream myStream = new FileInputStream(myXLSheet);                                
			HSSFWorkbook myWB = new HSSFWorkbook(myStream);                                
			HSSFSheet mySheet = myWB.getSheet(SheetName);                                 
			myRows = mySheet.getLastRowNum()+1;                                
			myCols = mySheet.getRow(0).getLastCellNum();                                
			xData = new String[myRows][myCols];        
			for (int i = 0; i < myRows; i++) {                           
					HSSFRow row = mySheet.getRow(i);
					for (int j = 0; j < myCols; j++) {                               
						HSSFCell cell = row.getCell(j);                               
						String value = cellToString(cell);                               
						xData[i][j] = value;                               
						}        
					}                                      
			return xData;
		}

	

	
	

	//Method/Function to Change cell type
		public static String cellToString(HSSFCell cell) { 
						int type = cell.getCellType();                        
			Object result;                        
			switch (type) {                            
				case HSSFCell.CELL_TYPE_NUMERIC: //0                                
					result = cell.getNumericCellValue();                                
					break;                            
				case HSSFCell.CELL_TYPE_STRING: //1                                
					result = cell.getStringCellValue();                                
					break;                            
				case HSSFCell.CELL_TYPE_FORMULA: //2                                
					throw new RuntimeException("We can't evaluate formulas in Java");  
					case HSSFCell.CELL_TYPE_BLANK: //3                                
					result = "-";                                
					break;                            
				case HSSFCell.CELL_TYPE_BOOLEAN: //4     
					result = cell.getBooleanCellValue();       
					break;                            
				case HSSFCell.CELL_TYPE_ERROR: //5       
					throw new RuntimeException ("This cell has an error");    
				default:                  
					throw new RuntimeException("We don't support this cell type: " + type); 
					}                        
			return result.toString();      
			}


	@After
	public void teardown()throws Exception{
	//mydriver.quit();
}
}
	


