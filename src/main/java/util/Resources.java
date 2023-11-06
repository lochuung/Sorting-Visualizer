package util;

import java.net.URL;

public class Resources {
    public static URL getResource(String path) {
        return Resources.class.getClassLoader().getResource(path);
    }
}
