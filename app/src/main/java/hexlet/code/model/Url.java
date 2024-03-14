package hexlet.code.model;

import java.sql.Timestamp;
import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class Url {
    private Long id;
    private final String name;
    private final Timestamp createdAt;
}
