package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrlChecksRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck, Url url) throws SQLException {
        String sql = "INSERT INTO url_checks (url_id, status_code, title, h1, description) VALUES (?, ?, ?, ?, ?)";

        try (var conn = getDataSource().getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, url.getId());
            preparedStatement.setInt(2, urlCheck.getStatusCode());
            preparedStatement.setString(3, urlCheck.getTitle());
            preparedStatement.setString(4, urlCheck.getH1());
            preparedStatement.setString(5, urlCheck.getDescription());
            preparedStatement.executeUpdate();
        }
    }

    public static List<UrlCheck> getEntitiesByUrl(Url url) throws SQLException {
        var sql = "SELECT id, status_code, title, h1, description, created_at"
                  + " FROM url_checks"
                  + " WHERE url_id = ?"
                  + " ORDER BY id DESC";
        try (var conn = getDataSource().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, url.getId());
            var resultSet = stmt.executeQuery();
            var urlChecks = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                var urlCheck = new UrlCheck(url,
                        resultSet.getInt("status_code"),
                        resultSet.getString("title"),
                        resultSet.getString("h1"),
                        resultSet.getString("description")
                );
                urlCheck.setId(resultSet.getLong("id"));
                urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
                urlChecks.add(urlCheck);
            }
            return urlChecks;
        }
    }
}
