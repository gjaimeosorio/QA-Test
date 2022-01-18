package com.example;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;


public class StepDefinitions {

    private WebDriver webDriver;
    private String baseUrl = "http://automationpractice.com/index.php";
    Actions action;
    By searchLogin = By.className("login");
    
    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
    }
    
    @Given("that Gabriel wants to create a new user profile")
    public void open_google_com() {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.get(baseUrl);
        webDriver.findElement(By.className("login")).click();
    }

    @When("he fill all forms to create a new user")
    public void he_fill_all_forms_to_create_a_new_user() throws Throwable {
        webDriver.findElement(By.id("email")).sendKeys("gabriel@choucair.com");
        webDriver.findElement(By.id("SubmitCreate")).click();  

        if(validateUser(By.id("create_account_error"))){
            webDriver.findElement(By.id("email_create")).sendKeys("gabriel@choucair.com");
            webDriver.findElement(By.id("passwd")).sendKeys("12345678");
            webDriver.findElement(By.id("SubmitLogin")).click(); 
        }else{
            webDriver.findElement(By.id("id_gender1")).click();

            webDriver.findElement(By.id("customer_firstname")).sendKeys("Gabriel");
            webDriver.findElement(By.id("customer_lastname")).sendKeys("Osorio");
            webDriver.findElement(By.id("passwd")).sendKeys("12345678");

            Select selectDay = new Select(webDriver.findElement(By.name("days")));
            selectDay.selectByValue("5");
            Select selectMonth = new Select(webDriver.findElement(By.name("months")));
            selectMonth.selectByValue("8");
            Select selectYear = new Select(webDriver.findElement(By.name("years")));
            selectYear.selectByValue("1995");

            webDriver.findElement(By.id("firstname")).sendKeys("Gabriel");
            webDriver.findElement(By.id("lastname")).sendKeys("Osorio");
            
            webDriver.findElement(By.id("address1")).sendKeys("Carrera 123 # 456 Sur 789");
            webDriver.findElement(By.id("city")).sendKeys("Medellin");

            Select selectState = new Select(webDriver.findElement(By.name("id_state")));
            selectState.selectByVisibleText("Georgia"); //or selectState.selectByValue("5");
            webDriver.findElement(By.id("postcode")).sendKeys("07463");

            webDriver.findElement(By.id("phone_mobile")).sendKeys("987654321");
            
            webDriver.findElement(By.id("submitAccount")).click();
        }

        

        
    }
    
    @Test
    @Then("he should buy 3 T-shirt size L and checkout it")
    public void he_should_buy_a_t_shirt_and_pay_it() throws InterruptedException {
        action = new Actions(webDriver);
        Thread.sleep(2000);

        webDriver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[3]/a")).click();

        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[1]/div/a[1]/img"));
        action.moveToElement(element).build().perform();
        
        webDriver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li/div/div[2]/div[2]/a[2]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"quantity_wanted\"]")).clear();
        webDriver.findElement(By.xpath("//*[@id=\"quantity_wanted\"]")).sendKeys("3");
        Select selectState = new Select(webDriver.findElement(By.id("group_1")));
        selectState.selectByVisibleText("L");

        webDriver.findElement(By.cssSelector("p#add_to_cart span")).click();

        //esperar a que cargue el carrito de compras
        Thread.sleep(7000);

        webDriver.findElement(By.cssSelector("div#layer_cart a > span")).click();
        Thread.sleep(2000);

        webDriver.findElement(By.cssSelector("div#center_column a.button.btn.btn-default.standard-checkout.button-medium > span")).click();
        Thread.sleep(2000);

        webDriver.findElement(By.cssSelector("div#center_column button[type=\"submit\"] > span")).click();
        Thread.sleep(2000);
        
        webDriver.findElement(By.name("cgv")).click();
        webDriver.findElement(By.cssSelector("form#form button[type=\"submit\"] > span")).click();

        //Informe para ver si son 3 prendas
        assertEquals(webDriver.findElement(By.xpath("//*[@id=\"product_1_5_0_632979\"]/td[5]/span")).getText(), "3");
        Thread.sleep(6000);
        webDriver.quit();
    }

    //@Test
    //public void validateAddToCarta() throws InterruptedException{
       //}

    //Validar usuario creado
    public boolean validateUser(By by) throws Throwable {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(webDriver, 45);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            status = true;
            return status;
        } catch (Exception e) {
            return status;
        } 
    }


                
}
    
