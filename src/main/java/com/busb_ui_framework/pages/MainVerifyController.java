package com.busb_ui_framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainVerifyController {
    WebDriver driver;
    public MainVerifyController(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements( driver, this);
    }
}
