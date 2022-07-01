package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class config {
    static int port ;
    static String host;

    static void readConfig() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(config.class.getResource("config.properties").getPath()));
            port = Integer.parseInt(properties.getProperty("port"));
            System.out.println("port: " + port);
            host = properties.getProperty("host");
        }
        catch (Exception e){
            port = 8000;
            host = "localhost";
        }
    }

    public static void main(String[] args) {
        try {
            readConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
