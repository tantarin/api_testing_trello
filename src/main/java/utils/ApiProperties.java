package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiProperties extends Properties {

    protected static ApiProperties instance;

    public static ApiProperties getInstance() {
        if (instance == null) {
            instance = new ApiProperties();
            try {
                instance.load(new FileInputStream(
                        "src/main/resources/api.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
