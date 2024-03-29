package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.repository.UrlsRepository;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.Collections;

public class UrlController {
    public static void index(Context ctx) throws SQLException {
        var page = new UrlsPage(UrlsRepository.getUrlsAndLastCheck());
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("page", page));
    }
}
