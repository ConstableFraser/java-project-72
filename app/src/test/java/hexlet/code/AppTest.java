package hexlet.code;

import static hexlet.code.App.readResourceFile;
import static hexlet.code.controller.RootController.buildUrl;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {
    private Javalin app;
    private static MockWebServer mockServer;
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private static final int NOT_FOUND_CODE = 404;

    @BeforeAll
    public static void mockStart() throws IOException {
        mockServer = new MockWebServer();
        var mockResponse = new MockResponse().setBody(readResourceFile("fixtures/testPage.html"));
        mockServer.enqueue(mockResponse);
    }

    @AfterAll
    public static void mockStop() throws IOException {
        mockServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    // утверждаем, что на главной странице есть элемент name="url"
    public void testMainPage() {
        var nameInput = "name=\"url\"";
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.home());
            assertThat(response.code()).isEqualTo(SUCCESS_CODE);
            assertThat(response.body()).isNotNull();
            assertThat(response.body().string()).contains(nameInput);
        });
    }

    @Test
    // утверждаем, что на главной странице присутствует текст шаблона
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urls());
            assertThat(response.code()).isEqualTo(SUCCESS_CODE);
            assertThat(response.body()).isNotNull();
            var responseBody = response.body().string();
            assertThat(responseBody).contains("Сайты");
            assertThat(responseBody).contains("сайты ещё не добавлены");
            assertThat(UrlsRepository.getEntities()).hasSize(0);
        });
    }

    @Test
    // утверждаем, что корректный URL успешно проходит проверку и добавляется в БД с кодом 200
    // утверждаем, что сайт-дубль не добавляется в БД и сопровождается сообщением
    public void testUrlControllerAddCorrectUrl() {
        JavalinTest.test(app, (server, client) -> {
            var correctUrl = "http://www.hexlet.io";
            var response = client.post(NamedRoutes.urls(), "url=" + correctUrl);
            var responseBody = response.body() == null ? "" : response.body().string();

            assertThat(response.code()).isEqualTo(SUCCESS_CODE);
            assertThat(responseBody).contains(correctUrl);
            assertThat(responseBody).contains("Страница успешно добавлена");
            assertThat(UrlsRepository.isExist(correctUrl)).isEqualTo(true);

            response = client.post(NamedRoutes.urls(), "url=" + correctUrl);
            responseBody = response.body() == null ? "" : response.body().string();
            assertThat(responseBody).contains("Страница уже существует");
            assertThat(UrlsRepository.getEntities()).hasSize(1);
        });
    }

    @Test
    // утверждаем, что НЕкорректный URL вызывает сообщение об ошибке и 400 код
    public void testUrlControllerWrongUrl() {
        JavalinTest.test(app, (server, client) -> {
            var wrongUrl = "www mailer com";
            var response = client.post(NamedRoutes.urls(), "url=" + wrongUrl);

            assertThat(response.code()).isEqualTo(ERROR_CODE);
            assertThat(response.body()).isNotNull();
            assertThat(response.body().string()).contains("Некорректный URL");
        });
    }

    @Test
    // утверждаем, что проверка некорректного URL сопровождается сообщением об ошибке адреса
    public void testUrlCheckControllerWrongURL() throws SQLException {
        var url = new Url("wrong URL");
        url.setId(1L);
        url.setCreatedAt(new Timestamp(new Date().getTime()));
        UrlsRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlCheck("1"));
            assertThat(response.code()).isEqualTo(ERROR_CODE);
            assertThat(response.body()).isNotNull();
            assertThat(response.body().string()).contains("Некорректный адрес");
        });
    }

    @Test
    // утверждаем, что запрос несуществующего Url'а сопровождается сообщением об ошибке
    public void testShowControllerNonExistUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath("100"));
            assertThat(response.code()).isEqualTo(NOT_FOUND_CODE);
            assertThat(response.body()).isNotNull();
            assertThat(response.body().string()).contains("Url with id = 100 not found");
        });
    }

    @Test
    // утверждаем, что контроллер корректно парсит фикстуру
    public void testUrlCheckControllerCorrectURL() throws SQLException {
        Url url = new Url(mockServer.url("/").toString());
        url.setId(1L);
        url.setCreatedAt(new Timestamp(new Date().getTime()));
        UrlsRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlCheck("1"));
            assertThat(response.code()).isEqualTo(SUCCESS_CODE);
            var lastCheck = UrlChecksRepository.getEntitiesByUrl(url);
            assertThat(lastCheck).hasSize(1);
            assertThat(lastCheck.get(0).getTitle()).isEqualTo("Пластиковые окна в Салехарде");
            assertThat(lastCheck.get(0).getH1()).isEqualTo("Пластиковые окна");
            assertThat(lastCheck.get(0).getDescription()).isEqualTo("Качественные окна лучшие в мире");
        });
    }

    @Test
    // утверждаем, что метод построения URL'а корректно обрабатывает порт
    public void testBuildUrl() throws URISyntaxException, MalformedURLException {
        var url = buildUrl(new URI("http://mirumir.org:8080").toURL());
        assertThat(url).isEqualTo("http://mirumir.org:8080");
    }
}
