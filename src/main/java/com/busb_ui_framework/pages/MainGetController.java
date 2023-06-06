package com.busb_ui_framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainGetController {
    WebDriver driver;

    public MainGetController(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
