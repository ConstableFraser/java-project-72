package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

public final class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }
    public static Javalin getApp() {
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.fileRenderer(new JavalinJte());
        });

        app.get(NamedRoutes.home(), RootController::home);

        return app;
    }

    public static void main(String[] args) {
        var app = getApp();
        app.start(getPort());
    }
}
