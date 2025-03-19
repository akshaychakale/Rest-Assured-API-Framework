package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class TestUtils {

    public static RequestSpecification req;
    public RequestSpecification requestSpecification() throws IOException {
        if(req==null)
        {
            PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
            req=new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

//    public static String getGlobalValue(String key) throws IOException
//    {
//        Properties prop =new Properties();
//        FileInputStream fis =new FileInputStream("E:\\RestAssuredFramework\\config\\global.properties");
//        prop.load(fis);
//        return prop.getProperty(key);
//    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        // Get the current working directory
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/config/global.properties";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            prop.load(fis);
        }
        String value = prop.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Key '" + key + "' not found in the properties file.");
        }
        return value;
    }

    public String getJsonPath(Response response, String key)
    {
        String resp=response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }

}
