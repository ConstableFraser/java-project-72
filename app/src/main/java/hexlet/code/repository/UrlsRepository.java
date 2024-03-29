package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UrlsRepository extends BaseRepository {
    static final int TIMESTAMP = 3;

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name) VALUES (?)";
        try (var conn = getDataSource().getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.executeUpdate();
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT id, name, created_at FROM urls WHERE id = ?";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                var idd = resultSet.getLong(1);
                var name = resultSet.getString(2);
                var createdAt = resultSet.getTimestamp(TIMESTAMP);
                var url = new Url(name);
                url.setId(idd);
                url.setCreatedAt(createdAt);

                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static boolean isExist(String name) throws SQLException {
        String sql = "SELECT id FROM urls WHERE name = ?";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();

            return resultSet.next();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT id, name FROM urls ORDER BY id";
        try (var conn = getDataSource().getConnection();
            var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var url = new Url(name);
                url.setId(id);
                result.add(url);
            }
            return result;
        }
    }

    public static List<Url> getUrlsAndLastCheck() throws SQLException {
        var urls = getEntities();
        for (Url url : urls) {
            var checks = UrlChecksRepository.getEntitiesByUrl(url);
            var lastCheck = checks.stream()
                    .max(Comparator.comparing(UrlCheck::getId));
            url.setLastCheck(lastCheck.orElse(null));
        }
        return urls;
    }
}
