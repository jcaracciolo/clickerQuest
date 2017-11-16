package ar.edu.itba.paw.webapp.DTO;

import java.net.URI;

public class ResourceManager {
    public static String resolveResource(URI baseUri, String resourcePath) {
        String path = baseUri.getPath();
        return baseUri.toString().replace(path,"") + "/api/resources/" + resourcePath;
    }
}
