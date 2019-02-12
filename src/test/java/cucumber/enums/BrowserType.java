package cucumber.enums;

public enum BrowserType {

    CHROME("chrome"),
    FIREFOX("firefox");

    private String value;

    BrowserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
