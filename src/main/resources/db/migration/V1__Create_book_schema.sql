CREATE TABLE IF NOT EXISTS author(
    rating  FLOAT(53),
    name    VARCHAR(255) NOT NULL CONSTRAINT author_pk PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS book
(
    page_count      INTEGER,
    rating          FLOAT(53),
    subtitle        VARCHAR(1024),
    title           VARCHAR(1024),
    description     LONGVARCHAR,
    isbn            VARCHAR(255) NOT NULL CONSTRAINT book_pk PRIMARY KEY,
    language        VARCHAR(255),
    preview_link    VARCHAR(255),
    published_date  VARCHAR(255),
    publisher       VARCHAR(255),
    thumbnail_url   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS category(
    name VARCHAR(255) NOT NULL CONSTRAINT category_pk PRIMARY KEY
);