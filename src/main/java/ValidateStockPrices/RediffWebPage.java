package ValidateStockPrices;



import java.util.ArrayList;

import java.util.HashMap;

import java.util.HashSet;

import java.util.List;

import java.util.Map;

import java.util.Set;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;



import org.w3c.dom.Document;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;



public class RediffWebPage {

	private static Set<Companies> companies= new HashSet<Companies>();

	private static  class Companies

	{

		String company;

		Double prevPrice;

		Double curPrice;

		Double change;

		Companies (String company,Double prev, Double cur,Double change2)

		{

			this.company=company;

			this.prevPrice=prev;

			this.curPrice=cur;

			this.change=change2;

		}

	}

	private static WebDriver driver;

	private static final String Url="https://money.rediff.com/losers/bse/daily/groupall";

public static void main(String[] args)

{

	InitializeWebDriver();

	try {

		if(!validateData())

		{

			System.out.println("Not same");

		}else

		{

			System.out.println("Same");

		}

	}catch(Exception e) {System.out.println("Exception="+e.getMessage());}

	finally {

	Close();

	}

}

private static void Close()

{

	  driver.close();

	  driver.quit();

}

private static boolean validateData() throws Exception

{

	driver.get(Url);

	try {

	ReadXML();

	}catch(Exception ex)

	{

		System.out.println("Exception in readxml= "+ex.getMessage());

	}

	

	List<WebElement> elements=driver.findElements(By.xpath("//table[@class=\"dataTable\"]/tbody/tr"));

	

	System.out.println("Total elements ="+elements.size());

	Map<String,Companies> map=new HashMap<String,Companies>();

	 

	//for(int i=1;i<=elements.size();i++)

	for(int i=1;i<=200;i++)

	{

		

		//WebElement element =driver.findElement(By.xpath("//table[@class=\"dataTable\"]/tbody/tr["+i+"]"));

		String company=driver.findElement(By.xpath("//table[@class=\"dataTable\"]/tbody/tr["+i+"]/td[1]")).getText();

		System.out.println(company);

		Double prev=Double.parseDouble(driver.findElement(By.xpath("//table[@class=\"dataTable\"]/tbody/tr["+i+"]/td[3]")).getText().replace(",",""));

		Double cur=Double.parseDouble(driver.findElement(By.xpath("//table[@class=\"dataTable\"]/tbody/tr["+i+"]/td[4]")).getText().replace(",",""));

		Double change=Double.parseDouble(driver.findElement(By.xpath("//table[@class=\"dataTable\"]/tbody/tr["+i+"]/td[5]")).getText().replace(",",""));

        

		map.put(company,new Companies(company,prev,cur,change));

	}

	for(Companies company:companies)

	{

		Companies webData= map.get(company.company);

		if(null==webData)

			return false;

		if(!((company.prevPrice==webData.prevPrice)&(company.curPrice==webData.curPrice)&(company.change==webData.change)))

		{

			return false;

		}

	}

	return true;

}

private static void ReadXML() throws Exception

{

	File file=null;

	try {

	file = new File("C:\\temp1\\VK\\test\\TestData.xml");

	}catch(Exception ex)

	{

		System.out.println("exception in file ="+ex.getMessage());

	}

	try {

	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	DocumentBuilder db = dbf.newDocumentBuilder();

	Document document = db.parse(file);

	NodeList nodeList=document.getElementsByTagName("Companies");

	for(int i=0;i<nodeList.getLength();i++)

	{

		try {

		Node node= nodeList.item(i);		

		String Company = node.getChildNodes().item(1).getTextContent();

		System.out.println("Company= "+ Company);

		Node node1 = node.getChildNodes().item(1);

		Double prev = Double.parseDouble(node.getChildNodes().item(3).getTextContent());

		Double cur = Double.parseDouble(node.getChildNodes().item(5).getTextContent());

		System.out.println("Change Value = "+ node.getChildNodes().item(7).getTextContent());

		Double change = Double.parseDouble(node.getChildNodes().item(7).getTextContent());

		System.out.println("Company="+Company+" prev="+ prev+" Cur="+cur+" change="+change);

		companies.add(new Companies(Company,prev,cur,change));

		}catch(Exception ex)

		{

			System.out.println("in for loop"+ex.getMessage());

		}

	}

	}catch(Exception ex)

	{

		System.out.println("Exception in READ XML function = "+ ex.getMessage());

	}

}

private static void InitializeWebDriver()

{

		System.setProperty("webdriver.chrome.driver","C:\\temp1\\contactUS\\chromeDriver_119\\chromedriver.exe");  

		ChromeOptions options = new ChromeOptions();

		options.setBinary("C:\\temp1\\contactUS\\chrome_119_105\\chrome.exe");

		//options.AddUserProfilePreference("profile.default_content_setting_values.cookies", 2);

		Map<String, Object> prefs = new HashMap<String, Object>();

		// Pass the argument 1 to allow and 2 to block

		prefs.put("profile.default_content_setting_values.cookies", 2);

		prefs.put("network.cookie.cookieBehavior", 2);

		prefs.put("profile.block_third_party_cookies", true);

		options.setExperimentalOption("prefs", prefs);

		driver = new ChromeDriver(options);

		driver.manage().window().maximize();

}



}