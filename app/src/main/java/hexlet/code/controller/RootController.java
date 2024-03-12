package hexlet.code.controller;

import hexlet.code.dto.RootPage;
import io.javalin.http.Context;
import java.util.Collections;

public class RootController {
    public static void home(Context ctx) {
        var page = new RootPage("Hello World");
        ctx.render("root.jte", Collections.singletonMap("page", page));
    }
}
