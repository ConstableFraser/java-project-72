package hexlet.code.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Setter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Url {
    private final String name;
    private Long id;
    private Timestamp createdAt;
    private List<Url> urls;
}
