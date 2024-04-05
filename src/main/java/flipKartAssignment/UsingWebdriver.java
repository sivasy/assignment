package flipKartAssignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;

import java.io.*;
import java.net.*;

public class UsingWebdriver {
private static WebDriver driver;
private static final String Url="https://www.flipkart.com/";
static Set<String> uniqueLinks=null;
  public static void main(String[] args) throws Exception {
 InitializeWebDriver();
 linksThruLambdaAndForEachAndParallelStream();
 linksThruLambdaAndForEachAndStream();
 Close();
  }
  private static void linksThruLambdaAndForEachAndStream() throws Exception
  {
 driver.get(Url);
 Thread.sleep(5*1000);
 driver.findElements(By.tagName("a")).stream().forEach((n) -> { uniqueLinks.add(n.getAttribute("href")); });
 Thread.sleep(5*1000);
 for (String link: uniqueLinks)
 {
 System.out.println(link);
 }
  }
  private static void linksThruLambdaAndForEachAndParallelStream() throws InterruptedException
  {
 driver.get(Url);
 Thread.sleep(5*1000);
 driver.findElements(By.tagName("a")).parallelStream().forEach((n) -> { uniqueLinks.add(n.getAttribute("href")); });
 driver.findElements(By.tagName("a")).stream().forEach((n) -> { uniqueLinks.add(n.getAttribute("href")); });
 Thread.sleep(5*1000);
 for (String link: uniqueLinks)
 {
 System.out.println(link);
 }

  }
  private static void Close()
  {
 driver.close();
 driver.quit();
  }
/*  i. For each loop
  ii. Stream
  iii. Parallel Stream
  iv. Lambda expression
*/
  public static void InitializeWebDriver()
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
uniqueLinks= new HashSet<String>();
driver = new ChromeDriver(options);
driver.manage().window().maximize();

  }
}
