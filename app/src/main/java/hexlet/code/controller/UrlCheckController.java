package hexlet.code.controller;

import hexlet.code.dto.urls.UrlChecksPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UrlCheckController {
    private static final int ERROR_CLIENT = 400;
    public static void check(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse(String.format("Url with id = %d not found", id)));

        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            Document doc = Jsoup.parse(response.getBody());
            var title = doc.selectFirst("title");
            var h1 = doc.selectFirst("h1");
            var description = doc.selectFirst("meta[name=description]");
            var urlCheck = new UrlCheck(url,
                    response.getStatus(),
                    title == null ? "" : title.wholeOwnText(),
                    h1 == null ? "" : h1.wholeOwnText(),
                    description == null ? "" : description.wholeOwnText());
            UrlChecksRepository.save(urlCheck, url);
            ctx.redirect(NamedRoutes.urlPath(String.valueOf(id)));
        } catch (Exception e) {
            var page = new UrlChecksPage(url, List.of());
            page.setFlash("Некорректный адрес");
            page.setFlashType("danger");
            ctx.status(ERROR_CLIENT);
            ctx.render("urls/show.jte", Collections.singletonMap("page", page));
        }
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlsRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse(String.format("Url with id = %d not found", id)));

        var page = new UrlChecksPage(url, UrlChecksRepository.getEntitiesByUrl(url));
        ctx.render("urls/show.jte", Collections.singletonMap("page", page));
    }
}
