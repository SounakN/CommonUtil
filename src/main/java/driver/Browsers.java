package driver;


public enum Browsers {

    EDGE,
    FIREFOX,
    CHROME;

    public static Browsers get(String browser) {
        return Browsers.valueOf(browser.trim().toUpperCase());
    }
}
