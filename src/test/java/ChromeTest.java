import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;


public class ChromeTest {
    private static WebDriver driver;

    @BeforeClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver",
                "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testTaskOne() throws InterruptedException {
        driver.get("http://www.kps42.ru/estate/");
        driver.findElement(By.xpath("//*[@id='form_filter']/div[1]/div[1]/div/div/button")).click();
        List<WebElement> allOptions = driver
                .findElements(By.xpath("//*[@id='form_filter']/div[1]/div[1]/div/div/div/ul/li"));

        ArrayList<String> dropDownElements = new ArrayList<String>();

        for (WebElement allOption : allOptions)
            dropDownElements.add(allOption.getText());

        for (String value : MainTestClass.categories) {
            assert(dropDownElements.contains(value)):
                    "Error: The element '" + value + "' not found!";
        }
    }

    @Test
    public void testTaskTwo() throws InterruptedException {
        driver.get("http://www.kps42.ru/estate/");

        Select dropdown = new Select(driver.findElement(By.id("category")));

        int options = dropdown.getOptions().size();

        ArrayList<Integer> countsElements = new ArrayList<Integer>();

        for (int i = 0; i < options; i++) {
            int count = 0;
            WebElement button = driver.findElement
                    (By.xpath("//*[@id='form_filter']/div[1]/div[1]/div/div/button"));
            button.click();
            WebElement allOptions = driver
                    .findElement(By.xpath("//*[@id='form_filter']/div[1]/div[1]/div/div/div/ul/li[" + (i + 1) + "]"));

            Actions actions = new Actions(driver);
            actions.moveToElement(allOptions.findElement(By.xpath(".//a/span[1]"))).click().build().perform();

            driver.findElement(By.xpath("//*[@id='form_filter']/div[3]/div[4]/div/button[1]")).click();

            WebElement table = driver.findElement(By.tagName("table"));
            List<WebElement> allRows = table.findElements(By.tagName("tr"));

            count += allRows.size() - 1;

            if (count == 50) {
                List<WebElement> allElements = driver.findElements(By.xpath("//div[@class='content-wrap']/ul/li"));
                if (!allElements.isEmpty()) {
                    count *= Integer.parseInt(allElements.get(allElements.size() - 2).getText()) - 1;

                    driver.findElement(By.linkText(allElements.get(allElements.size() - 2).getText())).click();

                    WebElement tableNew = driver.findElement(By.tagName("table"));
                    List<WebElement> allRowsNew = tableNew.findElements(By.tagName("tr"));
                    count += allRowsNew.size() - 1;
                }
            }
            countsElements.add(count);
        }

        for (Integer countsElement : countsElements) {
            assert(MainTestClass.countElements.contains(countsElement)):
                    "The count not matching!";
        }
    }

    @Test
    public void testTaskThreeAsc() throws InterruptedException {
        driver.get("http://www.kps42.ru/estate/");

        driver.findElement(By.partialLinkText("Цена, руб.")).click();

        List<WebElement> priceValues = driver.findElements
                (By.xpath("/html/body/div[1]/div/div[2]/div/table/tbody/tr/td[7]"));

        for (int i = 0; i < priceValues.size()-1; i++) {
            if (Integer.parseInt(priceValues.get(i+1).getText().replaceAll(" ", "")) <
                    Integer.parseInt(priceValues.get(i).getText().replaceAll(" ", ""))) {
                System.out.println("'acs' sorting false!");
                break;
            }

            if (i == priceValues.size()-1) {
                WebElement buttonNextPage =
                        driver.findElement(By.partialLinkText("дальше"));
                if (buttonNextPage != null) {
                    buttonNextPage.click();
                    i = 0;
                    priceValues = driver.findElements
                            (By.xpath("/html/body/div[1]/div/div[2]/div/table/tbody/tr/td[7]"));
                }
                else {
                    System.out.println("'asc' sorting true!");
                    break;
                }
            }
        }
    }

    @Test
    public void testTaskThreeDesc() throws InterruptedException {
        driver.get("http://www.kps42.ru/estate/");

        driver.findElement(By.partialLinkText("Цена, руб.")).click();
        driver.findElement(By.partialLinkText("Цена, руб.")).click();

        List<WebElement> priceValues = driver.findElements
                (By.xpath("/html/body/div[1]/div/div[2]/div/table/tbody/tr/td[7]"));

        for (int i = 0; i < priceValues.size()-1; i++) {
            if (Integer.parseInt(priceValues.get(i+1).getText().replaceAll(" ", "")) >
                    Integer.parseInt(priceValues.get(i).getText().replaceAll(" ", ""))) {
                System.out.println("'desc' sorting false!");
                break;
            }

            if (i == priceValues.size()-1) {
                WebElement buttonNextPage =
                        driver.findElement(By.partialLinkText("дальше"));
                if (buttonNextPage != null) {
                    buttonNextPage.click();
                    i = 0;
                    priceValues = driver.findElements
                            (By.xpath("/html/body/div[1]/div/div[2]/div/table/tbody/tr/td[7]"));
                }
                else {
                    System.out.println("'desc' sorting true!");
                    break;
                }
            }
        }
    }

    @Test
    public void testTaskFour() throws InterruptedException {
        driver.get("http://www.kps42.ru/estate/");

        Select dropdown = new Select(driver.findElement(By.id("category")));
        int options = dropdown.getOptions().size();

        for(int i = 0; i < options; i++){
            WebElement button = driver.findElement
                    (By.xpath("//*[@id='form_filter']/div[1]/div[1]/div/div/button"));
            button.click();
            WebElement allOptions = driver
                    .findElement(By.xpath("//*[@id='form_filter']/div[1]/div[1]/div/div/div/ul/li[" + (i + 1) + "]"));

            String nameOfEstate = allOptions.getText();

            Actions actions = new Actions(driver);
            actions.moveToElement(allOptions.findElement(By.xpath(".//a/span[1]"))).click().build().perform();

            driver.findElement(By.xpath("//*[@id='form_filter']/div[3]/div[4]/div/button[1]")).click();

            int priceOld = Integer.parseInt(driver.findElement
                    (By.xpath("/html/body/div[1]/div/div[2]/div/table/tbody/tr/td[7]")).getText().replaceAll(" ", ""));

            driver.findElement
                    (By.xpath("/html/body/div[1]/div/div[2]/div/table/tbody/tr/td[3]/a")).click();

            String priceNewStr[] = driver.findElement
                    (By.xpath("//div[@class='sell-price']/h3")).getText().split(" ");

            StringBuffer priceStr = new StringBuffer();
            for (int j = 0; j < priceNewStr.length-1; j++)
                priceStr.append(priceNewStr[j]);

            int priceNew = Integer.parseInt(String.valueOf(priceStr));

            if (priceOld == priceNew)
                System.out.println("Tab for '" + nameOfEstate + "' been opened and prices is matching");
            else
                System.out.println("Tab for '" + nameOfEstate + "' been opened and prices is not matching");

            driver.navigate().back();
        }
    }

    @AfterClass
    public static void theEnd() {
        driver.quit();
    }
}
