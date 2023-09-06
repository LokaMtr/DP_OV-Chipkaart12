import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
    private static Properties properties = new Properties();

    static {
        try {
            // Lees het eigenschappenbestand in
            FileInputStream fileInputStream = new FileInputStream("database.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
