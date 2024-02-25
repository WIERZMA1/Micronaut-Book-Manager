package org.book.manager.controller;

import io.micronaut.http.annotation.*;
import org.book.manager.domain.Author;
import org.book.manager.domain.AuthorResponse;
import org.book.manager.domain.BookResponse;
import org.book.manager.domain.CategoryResponse;
import org.book.manager.service.DbService;
import org.book.manager.service.GoogleBooksService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller("/api/v1")
public class BookController {

    private final GoogleBooksService googleBooksService;
    private final DbService service;

    public BookController(GoogleBooksService googleBooksService, DbService service) {
        this.googleBooksService = googleBooksService;
        this.service = service;
    }

    @Get("/googleBooks/query/{query}/maxResults/{maxResults}")
    public long saveBooks(String query, String maxResults) {
        Optional<Set<BookResponse>> books = googleBooksService.getBooksFromGoogleApi(query, maxResults);
        if (books.isPresent()) {
            return service.saveBooks(books.map(BookResponse::to).orElse(new HashSet<>()));
        }
        return 0;
    }

    @Get("/books")
    public Set<BookResponse> getBooks() {
        return BookResponse.from(service.getAllBooks());
    }

    @Get("/books/size")
    public long getBooksNumber() {
        return service.getNumberOfBooks();
    }

    @Get("/book/{isbn}")
    public BookResponse getBook(String isbn) throws BookNotFoundException {
        return BookResponse.from(service.getBook(isbn).orElseThrow(BookNotFoundException::new));
    }

    @Get("/book/search")
    public Set<BookResponse> searchBook(@QueryValue String str) {
        return BookResponse.from(service.searchBookContaining(str));
    }

    @Get("/category/{categoryName}/books")
    public Set<BookResponse> getBooksByCategory(String categoryName) {
        return BookResponse.from(service.getAllBooksByCategory(categoryName));
    }

    @Get("/author/{authorName}/books")
    public Set<BookResponse> getBooksByAuthor(String authorName) {
        return BookResponse.from(service.getAllBooksByAuthor(authorName));
    }

    @Delete("/book/{isbn}")
    public void deleteBook(@PathVariable("isbn") String isbn) {
        service.deleteBook(isbn);
    }

    @Put(value = "/book", consumes = APPLICATION_JSON)
    public BookResponse updateBook(@Body BookResponse bookDto) {
        return BookResponse.from(service.saveBook(BookResponse.to(bookDto)));
    }

    @Post(value = "/book", consumes = APPLICATION_JSON)
    public void createBook(@Body BookResponse bookDto) {
        service.saveBook(BookResponse.to(bookDto));
    }

    @Get("/authors")
    public Set<AuthorResponse> getAuthors() {
        return AuthorResponse.from(service.getAllAuthors());
    }

    @Get("/author/{name}")
    public AuthorResponse getAuthor(String name) {
        return AuthorResponse.from(service.getAuthor(name).orElse(new Author()));
    }

    @Get("/author/search")
    public Set<AuthorResponse> searchAuthor(@QueryValue String str) {
        return AuthorResponse.from(service.searchAuthorContaining(str));
    }

    @Get("/categories")
    public Set<CategoryResponse> getCategories() {
        return CategoryResponse.from(service.getAllCategories());
    }

    @Delete("/books")
    public void deleteBooks() {
        service.deleteAllBooks();
    }
}
