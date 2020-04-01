package org.fit.vips;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJS {
    DesiredCapabilities capabilities;
    WebDriver webDriver;

     public PhantomJS() {
         this.capabilities = new DesiredCapabilities();
         this.capabilities.setJavascriptEnabled(true);
         this.capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs");
         this.webDriver = new PhantomJSDriver(capabilities);
     }

     public String readStringHTML(String url) {
         this.webDriver.get(url);
         String rs = this.webDriver.getPageSource().replace("<!DOCTYPE html>", "").replace("&nbsp;", " ");
         webDriver.quit();
        return rs;
     }

    public static void main(String[] args) {
        // Creating a new instance of the HTML unit driver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs");
        WebDriver webDriver = new PhantomJSDriver(capabilities);
        webDriver.get("https://www.muabannhadat.vn/tin-dang/mua-ban-nha-pho-4-phong-ngu-quan-binh-thanh-9423664");
        System.out.println(webDriver.getPageSource());
    }
}
