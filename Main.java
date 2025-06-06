package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new EdgeDriver();
        driver.get("https://amazon.in");
        driver.manage().window().maximize();
        System.out.println("Application Title : "+driver.getTitle());

        WebElement searchBox = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
        searchBox.sendKeys("Wrist Watches");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement searchButton = driver.findElement(By.xpath("//input[@id='nav-search-submit-button'] "));
        searchButton.click();

        WebElement selectBrandTitan = driver.findElement(By.xpath("(//*[contains(text(),'Brands')])[2]//following::span[7]//i"));
        selectBrandTitan.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement selectDisplayTypeAnalogue = driver.findElement(By.xpath("(//*[contains(text(),'Display Type')])//following::i[1]"));
        selectDisplayTypeAnalogue.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement selectBandMaterialLeather = driver.findElement(By.xpath("(//*[contains(text(),'Watch Band Material')])//following::i[2]"));
        selectBandMaterialLeather.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement selectDiscount = driver.findElement(By.xpath("(//*[contains(text(),'25% Off or more')])"));
        selectDiscount.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        WebElement slider = driver.findElement(By.xpath("//*[@id='p_36/range-slider_slider-item']"));
        int sliderWidth = slider.getSize().getWidth();
        System.out.println("Slider width : "+sliderWidth);


        WebElement left = driver.findElement(By.xpath("//*[@id='p_36/range-slider_slider-item_lower-bound-slider']"));
        WebElement right = driver.findElement(By.xpath("//*[@id='p_36/range-slider_slider-item_upper-bound-slider']"));

        WebElement minAmt = driver.findElement(By.xpath("//label[@class='a-form-label sf-range-slider-label sf-lower-bound-label']"));
        WebElement maxAmt = driver.findElement(By.xpath("//label[@class='a-form-label sf-range-slider-label sf-upper-bound-label']"));


        String min = minAmt.getText().replaceAll("[^\\d]", ""); // Keep only digits
        int minPrice = Integer.parseInt(min);

        String max = maxAmt.getText().replaceAll("[^\\d]", ""); // Keep only digits
        int maxPrice = Integer.parseInt(max);

        int targetLeft = 6000;
        int targetRight = 8000;

        int leftOffset = (targetLeft - minPrice) * sliderWidth / (maxPrice - minPrice);
        int rightOffset = (maxPrice - targetRight) * sliderWidth / (maxPrice - minPrice);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", left);
        js.executeScript("arguments[0].setAttribute('style', 'left: " + leftOffset + "px;')", left);
        Thread.sleep(10000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", right);
        js.executeScript("arguments[0].setAttribute('style', 'left: " + (sliderWidth - rightOffset) + "px;')", right);
        Thread.sleep(10000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


        WebElement selectRange = driver.findElement(By.xpath("//*[@class='a-button-input']"));
        selectRange.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement selectFirstSearchResult = driver.findElement(By.xpath("(//a[@class='a-link-normal s-line-clamp-2 s-link-style a-text-normal'])[1]"));
        selectFirstSearchResult.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for(String window : childWindows) {
            if(!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        WebElement fetchPrice = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]"));
        System.out.println("Total Price : "+fetchPrice.getText());
        WebElement fetchDiscount = driver.findElement(By.xpath("(//span[@class='a-size-large a-color-price savingPriceOverride aok-align-center reinventPriceSavingsPercentageMargin savingsPercentage'])[1]"));
        System.out.println("Discount Applied : "+fetchDiscount.getText());
        WebElement fetchMrp = driver.findElement(By.xpath("(//span[@class='a-price a-text-price'])[1]"));
        System.out.println("M.R.P. : "+fetchMrp.getText());

        System.out.println("Passed!! ");
        driver.quit();
    }
}