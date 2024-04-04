DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls(
        id BIGINT GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
        name varchar(255) UNIQUE NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        CONSTRAINT pk_urls PRIMARY KEY (id)
);

CREATE TABLE url_checks(
        id BIGINT GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
        url_id BIGINT REFERENCES urls(id) ON DELETE CASCADE,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
        status_code INTEGER,
        title VARCHAR(255),
        h1 VARCHAR(255),
        description TEXT,
        FOREIGN KEY (url_id) REFERENCES urls (id),
        CONSTRAINT pk_url_checks PRIMARY KEY (id)
);
