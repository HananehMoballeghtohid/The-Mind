package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class config {
    private int port ;
    private String host;

     void readConfig() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(config.class.getClassLoader().getResource("config.properties").getPath()));
            port = Integer.parseInt(properties.getProperty("port"));
            host = properties.getProperty("host");
        }
        catch (Exception e){
            port = 8000;
            host = "localhost";
        }
    }

    public config(){
        try {
            readConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort (){
        return port;
    }

    public String getHost(){
        return host;
    }
}
