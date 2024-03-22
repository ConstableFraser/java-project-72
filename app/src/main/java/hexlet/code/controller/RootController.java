package hexlet.code.controller;

import hexlet.code.dto.RootPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collections;

public class RootController {
    public static void home(Context ctx) {
        var page = new RootPage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("root.jte", Collections.singletonMap("page", page));
    }

    public static void addUrl(Context ctx) throws SQLException {
        final int errorClient = 400;
        try {
            var userInputUrl = ctx.formParamAsClass("url", String.class)
                    .getOrDefault("");
            var url = buildUrl(new URI(userInputUrl).toURL());

            if (UrlsRepository.isExist(url)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flash-type", "info");
                ctx.redirect(NamedRoutes.urls());
                return;
            }

            Url objUrl = new Url(url);
            UrlsRepository.save(objUrl);

            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
            ctx.redirect(NamedRoutes.urls());
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
            var page = new RootPage();
            page.setFlash("Некорректный URL");
            page.setFlashType("danger");
            ctx.status(errorClient);
            ctx.render("root.jte", Collections.singletonMap("page", page));
        }
    }

    private static String buildUrl(URL url) {
        var protocol = url.getProtocol().isEmpty() ? "null" : url.getProtocol();
        String host = url.getHost().isEmpty() ? "null" : url.getHost();
        String port = url.getPort() == -1 ? "" : ":" + url.getPort();
        return String.format("%s://%s%s", protocol, host, port);
    }
}
