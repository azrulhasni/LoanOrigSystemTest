package org.azrul.loanorigsystem.loanorigsystemtest;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        try {
            int timeoutInSeconds = 30;
            String collateralId = null;
            String collateralAddressId = null;
            String currentAddressId = null;
            String previousAddressId = null;

            WebDriver browser = new FirefoxDriver();

            browser.get("http://localhost:8084/LoanOrigSystem");

            WebElement usernameField = browser.findElement(By.name("username"));
            usernameField.sendKeys("user1");

            WebElement passwordField = browser.findElement(By.name("password"));
            passwordField.sendKeys("password");

            WebElement searchButton = browser.findElement(By.name("submit"));
            searchButton.click();

            WebDriverWait wait = new WebDriverWait(browser, timeoutInSeconds);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.v-menubar-menuitem-caption")));

            //Create collateral address
            for (WebElement menu : browser.findElements(By.cssSelector("span.v-menubar-menuitem-caption"))) {
                if (menu.getText().equals("Create")) {
                    menu.click();
                    break;
                }
            }
            Thread.sleep(3000);

            for (WebElement newItem : browser.findElements(By.cssSelector("div.v-menubar-submenu.v-widget > span.v-menubar-menuitem > span.v-menubar-menuitem-caption"))) {
                if (newItem.getText().equals("New address")) {
                    newItem.click();
                    break;
                }
            }

            Thread.sleep(3000);
            browser.findElement(By.id("street address")).sendKeys("12 Toutou Road");

            browser.findElement(By.id("city")).sendKeys("Putrajaya");

            browser.findElement(By.id("country")).findElement(By.tagName("input")).sendKeys("Malaysia");
            Thread.sleep(1000);
            browser.findElement(By.id("country")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
            
            browser.findElement(By.id("state")).findElement(By.tagName("input")).sendKeys("Selangor");
            Thread.sleep(1000);
            browser.findElement(By.id("state")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
            
            browser.findElement(By.id("postal code")).sendKeys("40000");

            collateralAddressId = browser.findElement(By.id("address id")).getAttribute("value");

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);

            //create current address
            for (WebElement menu : browser.findElements(By.cssSelector("span.v-menubar-menuitem-caption"))) {
                if (menu.getText().equals("Create")) {
                    menu.click();
                    break;
                }
            }

            Thread.sleep(3000);

            for (WebElement newItem : browser.findElements(By.cssSelector("div.v-menubar-submenu.v-widget > span.v-menubar-menuitem > span.v-menubar-menuitem-caption"))) {
                if (newItem.getText().equals("New address")) {
                    newItem.click();
                    break;
                }
            }

            Thread.sleep(3000);
            browser.findElement(By.id("street address")).sendKeys("22 Main Road");

            browser.findElement(By.id("city")).sendKeys("Ipoh");

            browser.findElement(By.id("country")).findElement(By.tagName("input")).sendKeys("Malaysia");
            Thread.sleep(1000);
            browser.findElement(By.id("country")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
            browser.findElement(By.id("state")).findElement(By.tagName("input")).sendKeys("Perak");
            Thread.sleep(1000);
            browser.findElement(By.id("state")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);
            browser.findElement(By.id("postal code")).sendKeys("30020");

            currentAddressId = browser.findElement(By.id("address id")).getAttribute("value");
            previousAddressId = collateralAddressId; //put old house as collateral

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);

            //create collateral
            for (WebElement menu : browser.findElements(By.cssSelector("span.v-menubar-menuitem-caption"))) {
                if (menu.getText().equals("Create")) {
                    menu.click();
                    break;
                }
            }
            Thread.sleep(3000);

            for (WebElement newItem : browser.findElements(By.cssSelector("div.v-menubar-submenu.v-widget > span.v-menubar-menuitem > span.v-menubar-menuitem-caption"))) {
                if (newItem.getText().equals("New collateral")) {
                    newItem.click();
                    break;
                }
            }
            Thread.sleep(3000);
            
            browser.findElement(By.id("value")).sendKeys("600889");

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage address");

            Thread.sleep(3000);

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate existing");

            //lookup existing address for collateral
            Thread.sleep(3000);
            browser.findElement(By.id("address id")).sendKeys(collateralAddressId);
            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Search");
            Thread.sleep(3000);

            WebElement chosen = null;
            for (int i = 1; i < 4; i++) {
                chosen = browser.findElement(By.xpath("//div[@id='Associate']/div/div/div[4]/div/div/div[3]/div/div[2]/div/table/tbody/tr[" + i + "]/td/div"));
                if (chosen.getText() != null) {
                    break;
                }
            }
            chosen.click();
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate to current");

            Thread.sleep(3000);
            
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);

            //done address lookup
            collateralId = browser.findElement(By.id("collateral id")).getAttribute("value");

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);

            for (WebElement menu : browser.findElements(By.cssSelector("span.v-menubar-menuitem-caption"))) {
                if (menu.getText().equals("Create")) {
                    menu.click();
                    break;
                }
            }

            for (WebElement newItem : browser.findElements(By.cssSelector("div.v-menubar-submenu.v-widget > span.v-menubar-menuitem > span.v-menubar-menuitem-caption"))) {
                if (newItem.getText().equals("New application")) {
                    newItem.click();
                    break;
                }
            }
            Thread.sleep(3000);
            browser.findElement(By.id("account number")).sendKeys("CC1234567");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage product info");
             Thread.sleep(3000);
             
            //Add new product
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Add new");

            Thread.sleep(3000);

            browser.findElement(By.id("product")).findElement(By.tagName("input")).sendKeys("Credit Card");
            Thread.sleep(1000);
            browser.findElement(By.id("product")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);

            browser.findElement(By.id("advance")).sendKeys("300000");

            browser.findElement(By.id("advance as percentage of value")).sendKeys("26");

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");
            
            //done adding product
            
            //add applicant

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage applicants");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Add new");

            Thread.sleep(3000);
            List<WebElement> tabs = tabs = browser.findElements(By.xpath("(//div[contains(@class, 'v-tabsheet-tabitem')]/div[contains(@class, 'v-caption')])"));

            browser.findElement(By.id("annual salary")).sendKeys("130000");

            browser.findElement(By.id("monthly mortgage")).sendKeys("1100");

            browser.findElement(By.id("other income")).sendKeys("30000");

            browser.findElement(By.id("other outgoings")).sendKeys("3000");

            tabs.get(1).click();
            Thread.sleep(3000);

            browser.findElement(By.id("employer/company name")).sendKeys("My Company");

            browser.findElement(By.id("address 1")).sendKeys("Malaysia");

            browser.findElement(By.id("address 2")).sendKeys("Selangor");

            browser.findElement(By.id("city")).sendKeys("Shah Alam");

            browser.findElement(By.id("postal code")).sendKeys("43000");

            browser.findElement(By.id("phone number")).sendKeys("0139338499");

            tabs.get(2).click();
            Thread.sleep(3000);

            browser.findElement(By.id("fore name")).sendKeys("Kyle");

            browser.findElement(By.id("surname")).sendKeys("Hamilton");

            browser.findElement(By.id("age")).sendKeys("30");

            browser.findElement(By.id("date of birth")).findElement(By.tagName("input")).sendKeys("1984-03-14");

            browser.findElement(By.id("gender")).findElement(By.tagName("input")).sendKeys("Male");
            Thread.sleep(1000);
            browser.findElement(By.id("gender")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);

            browser.findElement(By.id("email address")).sendKeys("mark.hamilton@my.company.com");

            browser.findElement(By.id("marital status")).findElement(By.tagName("input")).sendKeys("Married");
            Thread.sleep(1000);
            browser.findElement(By.id("marital status")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);

            browser.findElement(By.id("time at current address")).sendKeys("32");
            
            //add current address
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage current address");
            Thread.sleep(3000);
            
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate existing");
            Thread.sleep(3000);
            
            browser.findElement(By.id("address id")).sendKeys(currentAddressId);
            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Search");
            Thread.sleep(3000);

            WebElement chosen4 = null;
            for (int i = 1; i < 4; i++) {
                chosen4 = browser.findElement(By.xpath("//div[@id='Associate']/div/div/div[4]/div/div/div[3]/div/div[2]/div/table/tbody/tr[" + i + "]/td/div"));
                if (chosen4.getText() != null) {
                    break;
                }
            }
            Thread.sleep(3000);
            chosen4.click();
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate to current");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");
            Thread.sleep(3000);
            //end adding current address
            //add previous address
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage previous address");
            Thread.sleep(3000);
            
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate existing");
            Thread.sleep(3000);
            
            browser.findElement(By.id("address id")).sendKeys(collateralAddressId);
            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Search");
            Thread.sleep(3000);

            WebElement chosen5 = null;
            for (int i = 1; i < 4; i++) {
                chosen5 = browser.findElement(By.xpath("//div[@id='Associate']/div/div/div[4]/div/div/div[3]/div/div[2]/div/table/tbody/tr[" + i + "]/td/div"));
                if (chosen5.getText() != null) {
                    break;
                }
            }
            Thread.sleep(3000);
            chosen5.click();
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate to current");

            Thread.sleep(3000);
            
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");
            Thread.sleep(3000);
            //end adding previosu address
            
            
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Add new");

            Thread.sleep(3000);
            tabs = tabs = browser.findElements(By.xpath("(//div[contains(@class, 'v-tabsheet-tabitem')]/div[contains(@class, 'v-caption')])"));

            //tabs = browser.findElements(By.className("v-tabsheet-tabitemcell"));
            browser.findElement(By.id("annual salary")).sendKeys("130000");

            browser.findElement(By.id("monthly mortgage")).sendKeys("1100");

            browser.findElement(By.id("other income")).sendKeys("30000");

            browser.findElement(By.id("other outgoings")).sendKeys("3000");

            tabs.get(1).click();
            Thread.sleep(3000);

            browser.findElement(By.id("employer/company name")).sendKeys("My Other Company");

            browser.findElement(By.id("address 1")).sendKeys("Malaysia");

            browser.findElement(By.id("address 2")).sendKeys("Selangor");

            browser.findElement(By.id("city")).sendKeys("Shah Alam");

            browser.findElement(By.id("postal code")).sendKeys("43000");

            browser.findElement(By.id("phone number")).sendKeys("0139338499");

            tabs.get(2).click();
            Thread.sleep(3000);

            browser.findElement(By.id("fore name")).sendKeys("Lisa");

            browser.findElement(By.id("surname")).sendKeys("Hamilton");

            browser.findElement(By.id("age")).sendKeys("29");

            browser.findElement(By.id("date of birth")).findElement(By.tagName("input")).sendKeys("1985-02-14");

            browser.findElement(By.id("gender")).findElement(By.tagName("input")).sendKeys("Female");
            Thread.sleep(1000);
            browser.findElement(By.id("gender")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);

            browser.findElement(By.id("email address")).sendKeys("lisa.hamilton@my.other.company.com");

            browser.findElement(By.id("marital status")).findElement(By.tagName("input")).sendKeys("Married");
            Thread.sleep(1000);
            browser.findElement(By.id("marital status")).findElement(By.tagName("input")).sendKeys(Keys.RETURN);

            browser.findElement(By.id("time at current address")).sendKeys("32");

            tabs.get(0).click();
            Thread.sleep(3000);

            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");
            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage collaterals");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate existing");

            Thread.sleep(3000);
            browser.findElement(By.id("collateral id")).sendKeys(collateralId);
            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Search");
            Thread.sleep(3000);

            WebElement chosen3 = null;
            for (int i = 1; i < 4; i++) {
                chosen3 = browser.findElement(By.xpath("//div[@id='Associate']/div/div/div[4]/div/div/div[3]/div/div[2]/div/table/tbody/tr[" + i + "]/td/div"));
                if (chosen3.getText() != null) {
                    break;
                }
            }
            chosen3.click();
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Associate to current");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Manage attachment");
            Thread.sleep(3000);
            browser.findElement(By.id("Upload files")).findElement(By.tagName("div"))
                    .findElement(By.tagName("div"))
                    .findElement(By.tagName("input"))
                    .sendKeys("C:\\attachments\\to_be_uploaded\\Java Concurrency - v1.pptx");
            Thread.sleep(5000);
            browser.findElement(By.id("Upload files")).findElement(By.tagName("div"))
                    .findElement(By.tagName("div"))
                    .findElement(By.tagName("input"))
                    .sendKeys("C:\\attachments\\to_be_uploaded\\Fast_Split_By_GINI.sql");
            Thread.sleep(5000);
            browser.findElement(By.id("Upload files")).findElement(By.tagName("div"))
                    .findElement(By.tagName("div"))
                    .findElement(By.tagName("input"))
                    .sendKeys("C:\\attachments\\to_be_uploaded\\kod.bi.zip");
            Thread.sleep(5000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Close");
            Thread.sleep(3000);
            (new WebDriverBackedSelenium(browser, browser.getCurrentUrl())).click("Save and back");

            // menus.get(1).click();
//            WebElement view1 = browser.findElement(By.cssSelector("div.v-menubar-submenu.v-widget > span.v-menubar-menuitem > span.v-menubar-menuitem-caption"));
//            view1.click();
//           
            //  "v-table-cell-content"
        } catch (InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
