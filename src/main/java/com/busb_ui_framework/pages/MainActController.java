package com.busb_ui_framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
public class MainActController {
    WebDriver driver;
    public MainActController(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements( driver, this);
    }

    public void fillFields(By selector, String value){
        String s = Keys.chord(Keys.CONTROL, "a");
        driver.findElement(selector).sendKeys(s);
        driver.findElement(selector).sendKeys(value);
    }
}
