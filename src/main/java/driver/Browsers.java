package driver;


public enum Browsers {

    EDGE,
    FIREFOX,
    CHROME;

    //Get the Enum
    public static Browsers get(String s) {
        return Browsers.valueOf(s.trim().toUpperCase());
    }
}
