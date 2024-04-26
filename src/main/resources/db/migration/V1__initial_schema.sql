CREATE TABLE SUPER_HERO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE SUPER_HERO_SUPER_POWERS (
    super_Hero_id BIGINT,
    super_Powers VARCHAR(255),
    FOREIGN KEY (super_Hero_id) REFERENCES SUPER_HERO(id),
    CONSTRAINT uc_superhero_superpowers UNIQUE (super_Hero_id, super_Powers)
);

CREATE TABLE SUPER_HERO_VULNERABILITIES (
    super_Hero_id BIGINT,
    vulnerabilities VARCHAR(255),
    FOREIGN KEY (super_Hero_id) REFERENCES SUPER_HERO(id),
    CONSTRAINT uc_superhero_vulnerabilities UNIQUE (super_Hero_id, vulnerabilities)
);
