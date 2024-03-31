package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

public class AppTest {
    private Javalin app;
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;

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
    // утверждаем, что корректный URL успешно проходит проверку и добавляется в БД с кодом 200
    // утверждаем, что попытка добавления сайта-дубля сопровождается сообщением
    public void testAddUrlControllerAddCorrectUrl() {
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
        });
    }

    @Test
    // утверждаем, что НЕкорректный URL вызывает сообщение об ошибке и 400 код
    public void testAddUrlControllerWrongUrl() {
        JavalinTest.test(app, (server, client) -> {
            var wrongUrl = "www mailer com";
            var response = client.post(NamedRoutes.urls(), "url=" + wrongUrl);

            assertThat(response.code()).isEqualTo(ERROR_CODE);
            assertThat(response.body()).isNotNull();
            assertThat(response.body().string()).contains("Некорректный URL");
        });
    }
}
