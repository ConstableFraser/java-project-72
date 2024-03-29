DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name varchar(255) UNIQUE NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE url_checks(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        url_id BIGINT REFERENCES urls(id) ON DELETE CASCADE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        status_code INT,
        title VARCHAR(255),
        h1 VARCHAR(255),
        description TEXT
);
