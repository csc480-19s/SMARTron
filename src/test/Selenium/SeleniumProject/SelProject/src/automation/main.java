package automation;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class main {

	private static int testCount = 0,passCount = 0,failCount = 0;
	
	public static void main(String[] args) throws InterruptedException
	{
		File base = new File("main.java");
		
		String path = base.getPath();
		
		path=path.replace("main.java", "");
		// Uses the Google Chrome Driver
		System.setProperty("webdriver.chrome.driver",path+"chromedriver_win32\\chromedriver.exe");
		
		// Initialize browser
		WebDriver mainDriver = new ChromeDriver();
		
		// driver.get("C:\\Users\\Quinn\\Desktop\\Coding\\Web-Dev Testing\\test.html");
		//Testing using selenium 
		// The website can be opened from html files or URLs
		// Our webapp is run from localhost:3000 or http://cs.oswego.edu/~smartron
		String loginEmail = "testselenium480@gmail.com";
		
		testLogin(mainDriver,loginEmail);
		testNamesDisplay(mainDriver,loginEmail);
		String temp = testCreateTestScan(mainDriver);
		checkChangeName(mainDriver,temp);
		testResults(mainDriver);
		testCreateTestScan(mainDriver);
		checkKey(mainDriver);
		testNewTestStillOnPage(mainDriver);
		testCreateTestScan(mainDriver);
		testChangeKey(mainDriver);
		logOutTest(mainDriver);
		
		mainDriver.quit();
		//mainDriver.close();
		printFinal();
	}

	public static void testLogin(WebDriver driver,String emailString) throws InterruptedException
	{
		//Navigates to localhost where our website is
		//driver.get("http://localhost:3000");
		driver.get("http://pi.cs.oswego.edu:13126/");
		driver.manage().window().maximize();
		// Can find elements on the page a number of ways, xPath is my preferred method.
		WebElement email = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/button"));
		email.click();

		// Handling pop ups
		String mainWindow = driver.getWindowHandle();

		// Handles all new opened window via iterator
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		i1.next();
		i1.next();
		
		while (i1.hasNext())
		{

			String childWindow = i1.next();

			if (!mainWindow.equalsIgnoreCase(childWindow))
			{

				// Switching to Child window
				driver.switchTo().window(childWindow);

				driver.manage().window().maximize();
				
				
				//Signing in by finding and filling out the email and password fields incorrectly
				WebElement user = driver.findElement(By.xpath("//*[@id=\"identifierId\"]"));
				//user.sendKeys("badEmail@gmall.com");
				//Thread.sleep(5000);
				
				
				WebElement nextButton = driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/content/span"));
				//nextButton.click();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//printMe(existsElement("//*[@id=\"view_container\"]/div/div/div[2]/div/div[1]/div/form/content/section/div/content/div[1]/div/div[2]/div[2]/div",driver),"Incorrect Login Test");
				
				//user.clear();
				//Signing in by finding and filling out the email and password fields
				user.sendKeys(emailString);
				nextButton.click();

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				WebElement password = driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"));
				
				password.sendKeys("HiPeople");
				WebElement nextButton2 = driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/content/span"));
				nextButton2.click();
			}
		}
		
		// Switching to main window
		driver.switchTo().window(mainWindow);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Checks for successful login by checking if the page element contains Welcome
		WebElement welcome = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/h1"));
		printMe(welcome.getText().contains("Welcome"),"TestLogin");
	}

	public static void testNamesDisplay(WebDriver driver,String email) {
		WebElement emailDisplay = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/header/div/h1"));
		
		printMe(emailDisplay.getText().equalsIgnoreCase(email),"Email display");
		
		String name = "Comp Science";
		
		WebElement usernameDisplay = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/h1"));
		String welcomeUser=usernameDisplay.getText();
		welcomeUser=welcomeUser.toLowerCase();
		name=name.toLowerCase();
		
		printMe(welcomeUser.contains(name),"Welcome username should contain "+name);
		
	}
	
	public static String testCreateTestScan(WebDriver driver) throws InterruptedException
	{
		String newTestName = "TestName01";
		
		//Testing if creating a new scan creates a new test box
		WebElement createButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/button"));
		createButton.click();
		
		WebElement testNameField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/div/form/label[1]/input"));
		testNameField.sendKeys(newTestName);
		
		WebElement testNumQuestions = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/div/form/label[2]/input"));
		testNumQuestions.sendKeys("35");
				
		WebElement submit = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/div/button"));
		submit.click();
		
		printMe(existsElement("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/div/div[2]/div/h1",driver),"Scan Code displayed");
		
		WebElement okButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/div/div[2]/div/button"));
		okButton.click();
		String examXPath = "//*[@id=\"root\"]/div/div/div[2]/div/a";
		printMe(existsElement(examXPath,driver),"New exam Displayed");
		return examXPath;
		//WebElement newExam = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[4]/div"));
		//printMe(newExam.getText().equals(newTestName),"New exam displays correct name");
		//Testing answer key of new exam has correct amount of questions
		//Thread.sleep(100000);
		
	}
	
	public static void checkChangeName(WebDriver driver, String examXPathString) {
		//if(existsElement("//*[@id=\"root\"]/div/div/div[2]/div/svg",driver)) {
		WebElement editBtn = driver.findElement(By.className("fa-pencil-alt"));
		editBtn.click();
	
		WebElement textField = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/form/label/input"));
		String text = "Edit Check 123-=!";
		textField.sendKeys(text);
		
		WebElement submitName = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/button[1]"));
		submitName.click();
		//*[@id="root"]/div/div/div[2]/div/a
		WebElement nameCheck = driver.findElement(By.xpath(examXPathString));
		printMe(text.equals(nameCheck.getText()),"Name Change Test");
	//} else {
		//printMe(false,"Name Change Btn Found");
	//}
	}
	
	public static void checkKey(WebDriver driver) {
		WebElement newExamAnswerKeyBtn=driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/button[2]"));
		newExamAnswerKeyBtn.click();
		
		boolean lastQuestionCheck = existsElement("//*[@id=\"root\"]/div/div/div[2]/div/div[35]",driver);
		boolean questionAfterLastCheck = !existsElement("//*[@id=\"root\"]/div/div/div[2]/div/div[36]",driver);
		printMe(lastQuestionCheck&&questionAfterLastCheck,"Newly created exam has correct amount of questions in answerKey");
		driver.navigate().back();
	}
	
	public static void testResults(WebDriver driver) throws InterruptedException
	{
		//Testing if the results display
		WebElement resultsButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/button[1]"));
		resultsButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//*[@id="root"]/div/div/div[2]/div/button[2]
		//checks if all the tabs on the results pags display
		if(existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/header/div/div/div/div/button[1]/span[1]/span",driver)
				&& existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/header/div/div/div/div/button[2]/span[1]/span",driver)
				&& existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/header/div/div/div/div/button[3]/span[1]/span",driver)) 
		{
			printMe(true,"Results page buttons displayed correctly");
			
			//Checks if the statistics tab works
			//WebElement statisticsButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/div/header/div/div/div/div/button[1]"));
			//statisticsButton.click();
	
			printMe(existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div[1]/table/thead/tr/th[1]",driver),"Statistics Report Table displayed");
			printMe(existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div[2]/table/thead/tr/th[1]",driver),"Statistics Report Table 2 displayed");
			//This was removed
			//printMe(existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/div/div/div[1]/div/div/div/div[3]/svg",driver),"Statistics Report Graph displayed correctly");
			
			//Checks if the by question results displays correctly
			WebElement byQuestionButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/div/header/div/div/div/div/button[2]"));
			byQuestionButton.click();
			
			Thread.sleep(1000);
			printMe(existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/div/div/div[2]/div/div/div/table/thead/tr/th[1]",driver),"By Question Table displays");
			printMe(existsElement("//*[@id=\"root\"]/div/div/div/div/div/div/div[2]/div/div/div[2]",driver),"By Question Graph displayed correctly");

			//Checks if the By student reults displays correctly
			WebElement byStudentsButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/div/header/div/div/div/div/button[3]"));
			byStudentsButton.click();
			
			printMe(existsElement("//*[@id=\"root\"]/div/div/div/div[2]/div/div/div/div/div[3]/div/div/div/table/thead/tr/th[1]",driver),"By Students table displayed correctly");
			
		}
		else
			printMe(false,"Results page buttons displayed correctly");
		
		driver.navigate().back();
		
	}
	
	public static void testNewTestStillOnPage(WebDriver driver) 
	{
		//Testing if a newly created exam is displayed after a redirect
		//driver.navigate().back();
		printMe(existsElement("//*[@id=\"root\"]/div/div/div[4]/div",driver),"Newly created exam stays on home page");
	}
	
	public static void testChangeKey(WebDriver driver)
	{
		
		
		WebElement keyButton = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/button[2]"));
		keyButton.click();
		WebElement keyHeading = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/h1[1]"));
		
		printMe(keyHeading.getText().equalsIgnoreCase("Answer Key"), "Answer Key Page is Navigated to correctly");
		
		printMe(existsElement("//*[@id=\"root\"]/div/div/div[2]/div/div[1]/div/div/span[1]",driver),"Test key displays");
	}
	
	public static void logOutTest(WebDriver driver) {
		WebElement loBtn = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/header/div/button"));
		loBtn.click();
		
		WebElement info = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/p"));
		printMe(info.getText().equals("Welcome to SMARTron, please login with your LakerNet account"),"Log Out Test");
	}
	
	//Checks if a passed XPath exists as an element on the page
	private static boolean existsElement(String xPath,WebDriver driver)
	{
	    try
	    {
	        driver.findElement(By.xpath(xPath));
	    }
	    catch (NoSuchElementException e)
	    {
	        return false;
	    }
	    return true;
	}
	
	//Prints the tests results
	public static void printMe(boolean passed, String test) 
	{
		testCount++;
		
		if (passed) 
		{
			passCount++;
			System.out.println(testCount + " " + test + " - has Passed!");
		}
		else
		{
				failCount++;
				System.out.println(testCount + " " + test + " - has Failed!");
		}
	}
	
	public static void printFinal() {
		double percentPass = ((double)passCount/testCount)*100.00;
		
		System.out.print("-" + passCount + "- tests passed and -" + failCount + "- tests failed out of -" + testCount + "- total tests. Giving us -" + (int)percentPass +"%- have passed.");;
	}
}

/*
 * Output from May 8th
Starting ChromeDriver 2.46.628402 (536cd7adbad73a3783fdc2cab92ab2ba7ec361e1) on port 16099
Only local connections are allowed.
Please protect ports used by ChromeDriver and related test frameworks to prevent access by malicious code.
May 08, 2019 11:45:10 AM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: OSS
1 TestLogin - has Passed!
2 Email display - has Passed!
3 Welcome username should contain comp science - has Passed!
4 Scan Code displayed - has Passed!
5 New exam Displayed - has Passed!
6 Name Change Test - has Passed!
7 Results page buttons displayed correctly - has Passed!
8 Statistics Report Table displayed - has Passed!
9 Statistics Report Table 2 displayed - has Passed!
10 Statistics Report Graph displayed correctly - has Failed!
11 By Question Table displays - has Passed!
12 By Question Graph displayed correctly - has Failed!
13 By Students table displayed correctly - has Passed!
14 Scan Code displayed - has Passed!
15 New exam Displayed - has Passed!
16 Newly created exam has correct amount of questions in answerKey - has Failed!
17 Newly created exam stays on home page - has Failed!
18 Scan Code displayed - has Passed!
19 New exam Displayed - has Passed!
20 Answer Key Page is Navigated to correctly - has Passed!
21 Test key displays - has Failed!
22 Log Out Test - has Passed!
-17- tests passed and -5- tests failed out of -22- total tests. Giving us -77%- have passed.
 *
 */