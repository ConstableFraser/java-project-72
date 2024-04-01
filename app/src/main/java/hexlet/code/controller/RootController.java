package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;

import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collections;

public class RootController {
    public static void home(Context ctx) {
        var page = new BasePage();
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
                var page = new UrlsPage(UrlsRepository.getEntities());
                page.setFlash("Страница уже существует");
                page.setFlashType("info");
                ctx.render("urls/index.jte", Collections.singletonMap("page", page));
                return;
            }

            Url objUrl = new Url(url);
            UrlsRepository.save(objUrl);

            var page = new UrlsPage(UrlsRepository.getEntities());
            page.setFlash("Страница успешно добавлена");
            page.setFlashType("success");
            ctx.render("urls/index.jte", Collections.singletonMap("page", page));
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
            var page = new BasePage();
            page.setFlash("Некорректный URL");
            page.setFlashType("danger");
            ctx.status(errorClient);
            ctx.render("root.jte", Collections.singletonMap("page", page));
        }
    }

    public static String buildUrl(URL url) {
        var protocol = url.getProtocol().isEmpty() ? "null" : url.getProtocol();
        String host = url.getHost().isEmpty() ? "null" : url.getHost();
        String port = url.getPort() == -1 ? "" : ":" + url.getPort();
        return String.format("%s://%s%s", protocol, host, port);
    }
}
