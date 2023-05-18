package util;

public class Configuration {

    private static final Configuration instance = new Configuration();

    private final String baseUrl;
    private final String techDocsUrl;

    private Configuration() {
        this.baseUrl = "https://www.lely.com/en";
        this.techDocsUrl = "https://www.lely.com/techdocs/";
    }

    public static Configuration getInstance() {
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getTechDocsUrl() {
        return techDocsUrl;
    }
}
