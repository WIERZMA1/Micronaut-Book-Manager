CREATE TABLE IF NOT EXISTS books_authors(
    author_name VARCHAR(255) NOT NULL,
    book_id     VARCHAR(255) NOT NULL,
    PRIMARY KEY (author_name, book_id),
    FOREIGN KEY (book_id) REFERENCES book,
    FOREIGN KEY (author_name) REFERENCES author
);

CREATE TABLE IF NOT EXISTS books_categories(
    book_id         VARCHAR(255) NOT NULL,
    category_name   VARCHAR(255) NOT NULL,
    PRIMARY KEY     (book_id, category_name),
    FOREIGN KEY     (book_id) REFERENCES book,
    FOREIGN KEY     (category_name) REFERENCES category
);