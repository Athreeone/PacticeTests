import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class MainPage {

    WebDriver driver;
    WebDriverWait wait;

    String actualUrlMainPage = "https://xn--e1ajpedda3g.xn----etbpba5admdlad.xn--p1ai/";
    String actualUrlRegisterPage = "https://xn--e1ajpedda3g.xn----etbpba5admdlad.xn--p1ai/register";
    String successRegisterUrl = "https://xn--e1ajpedda3g.xn----etbpba5admdlad.xn--p1ai/register/success";
    String linkVK = "https://vk.com/torgi_russii_bankrotstvo";

    @BeforeMethod
   public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\athre\\IdeaProjects\\chromedriver-win64\\chromedriver.exe");
        driver  = new ChromeDriver();

        driver.get("https://xn--e1ajpedda3g.xn----etbpba5admdlad.xn--p1ai/");

    }
    @AfterMethod
    public void close() {
        driver.close();
    }
    @Test
    public void checkUrl() {
        assert driver.getCurrentUrl().equals(actualUrlMainPage);
        System.out.println(driver.getCurrentUrl());
    }
    @Test
    public void invalidRegisterForm() {
        wait = new WebDriverWait(driver,15);
        driver.findElement(By.cssSelector(".fs13.grey-text.inline.no-margin.no-padding")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#form_0_data_lastname"))).sendKeys("Ivanov");
        driver.findElement(By.cssSelector(".btn.btn-custom.blue.waves-effect.waves-light.w40")).click();
        WebElement errorClass = driver.findElement(By.cssSelector(".form-control.browser-default.field-input.error"));
        assert errorClass.isDisplayed();

    }
    @Test
    public void positiveSearching() {
        driver.findElement(By.cssSelector(".main-search-input")).sendKeys("#@!");
        driver.findElement(By.cssSelector("input[type = 'submit']")).click();
        WebElement positiveFound = driver.findElement(By.cssSelector(".container"));
        assert positiveFound.isDisplayed();
    }
    @Test
    public void activeLogo() {
        driver.findElement(By.xpath("//*[@id=\"site-logo\"]/nobr/img[3]")).click();
        assert driver.getCurrentUrl().equals(actualUrlMainPage);
    }
    @Test
    public void openLeftMenu() {
        wait = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector("#siteMenu2")).click();
        WebElement openLeftMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sidenav-overlay")));
        assert openLeftMenu.isDisplayed();
    }
    @Test
    public void leftMenuRegisterUrl() {
        wait = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector("#siteMenu2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mainMenu\"]/a[4]/div/i"))).click();
        assert driver.getCurrentUrl().equals(actualUrlRegisterPage);
    }
    @Test
    public void leftMenuSuccessRegister() {
        wait = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector("#siteMenu2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mainMenu\"]/a[4]/div/i"))).click();
        driver.findElement(By.cssSelector("#form_0_data_lastname")).sendKeys("Ivanov");
        driver.findElement(By.cssSelector("#form_0_data_firstname")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("#form_0_data_middlename")).sendKeys("Sergeevich");
        driver.findElement(By.cssSelector("#form_0_data_passport_series")).sendKeys("6666");
        driver.findElement(By.cssSelector("#form_0_data_passport_number")).sendKeys("999999");
        driver.findElement(By.cssSelector("#form_0_data_issuer_name")).sendKeys("moumoukiskis");
        driver.findElement(By.cssSelector("#form_0_data_issue_date_visible")).sendKeys("11.11.1999");
        driver.findElement(By.cssSelector("#form_0_data_inn")).sendKeys("774301390299");
        driver.findElement(By.cssSelector("#form_0_data_address")).sendKeys("Moscow");
        driver.findElement(By.cssSelector("#form_0_data_phone")).sendKeys("9999999999");
        driver.findElement(By.cssSelector("#form_0_email")).sendKeys("bbb@mail.ru");
        driver.findElement(By.cssSelector("label[for = 'form_0_personal_data_handle_agreement']")).click();
        driver.findElement(By.cssSelector(".btn.btn-custom.blue.waves-effect.waves-light.w40")).click();
        assert driver.getCurrentUrl().equals(successRegisterUrl);
    }
    @Test
    public void invalidAuthorization() {
        wait = new WebDriverWait(driver,10);
        driver.findElement(By.cssSelector(".mdi.ti.ti-account.blue-text.text-darken-3")).click();
        driver.findElement(By.cssSelector("#login")).sendKeys("12345667");
        driver.findElement(By.cssSelector("#password")).sendKeys("QQQQQ");
        driver.findElement(By.cssSelector("button[type = 'submit']")).click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col.s12.red-text.valign-wrapper")));
        assert errorMessage.getText().contains("Неверный логин или пароль");
    }
    @Test
    public void iconVK() {
        wait = new WebDriverWait(driver,10);
        String originalWindow = driver.getWindowHandle();
        driver.manage().window();
        driver.findElement(By.cssSelector(".footer__social-link.footer__social-link--vk")).click();
        for (String currentWindow : driver.getWindowHandles()) {
            if (!currentWindow.equals(originalWindow)) {
                driver.switchTo().window(currentWindow);
                break;
            }
        }
        assert driver.getCurrentUrl().equals(linkVK);
    }
}
