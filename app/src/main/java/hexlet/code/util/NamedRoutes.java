package hexlet.code.util;

public class NamedRoutes {
    public static String home() {
        return "/";
    }

    public static String urls() {
        return "/urls";
    }

    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }
}
