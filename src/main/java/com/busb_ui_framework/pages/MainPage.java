package com.busb_ui_framework.pages;

import org.openqa.selenium.WebDriver;

// page_url SAT = https://sat-client-staging.saveatrain.com/#/
// page_url PROD = https://sat-client-staging.saveatrain.com/#/
public class MainPage {
    private MainActController act;
    private MainGetController get;
    private MainVerifyController verify;
    public MainActController act() {
        return act;
    }
    public MainGetController get() {
        return get;
    }
    public MainVerifyController verify() {
        return verify;
    }
    private MainPage(MainActController act, MainGetController get, MainVerifyController verify) {
        this.act = act;
        this.get = get;
        this.verify = verify;
    }

    public static MainPage getMainPage(WebDriver driver) {
        return new MainPage(new MainActController(driver), new MainGetController(driver),
                new MainVerifyController(driver));
    }
}
