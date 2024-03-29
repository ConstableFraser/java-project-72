package hexlet.code.model;

import java.sql.Timestamp;
import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UrlCheck {
    private Long id;
    private Timestamp createdAt;
    private final int statusCode;
    private final String title;
    private final String h1;
    private final String description;
    private final Url url;
}
