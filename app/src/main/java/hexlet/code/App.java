package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.RootController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

public final class App {
    private static final String SQL_FILEPATH = "schema.sql";

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    private static String readResourceFile() throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(App.SQL_FILEPATH);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
    public static Javalin getApp() throws IOException, SQLException {
        var app = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.fileRenderer(new JavalinJte());
        });

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDBUrl());

        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readResourceFile();

        // log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.setDataSource(dataSource);

        app.get(NamedRoutes.home(), RootController::home);

        return app;
    }

    public static String getDBUrl() {
        var dbUrl = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;";
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", dbUrl);
    }
    public static void main(String[] args) throws SQLException, IOException {
        var app = getApp();
        app.start(getPort());
    }
}
