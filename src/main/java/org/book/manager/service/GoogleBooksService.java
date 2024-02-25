package org.book.manager.service;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.book.manager.domain.BookResponse;
import org.book.manager.mapper.GoogleBooksResponse;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class GoogleBooksService {

    @Value("${google.books.api.url}")
    private String googleBooksApiUrl;

    private final HttpClient httpClient;
    private static final int MAX_RESULTS = 40;

    public Optional<Set<BookResponse>> getBooksFromGoogleApi(String query, String maxResults) {
        Set<BookResponse> books = new HashSet<>();
        int maxResultsSet = Integer.parseInt(maxResults);
        int startIndex = 0;
        do {
            HttpRequest<String> req = HttpRequest.GET(
                    UriBuilder
                            .of(googleBooksApiUrl)
                            .queryParam("q", query)
                            .queryParam("maxResults", String.valueOf(maxResultsSet - startIndex > 40
                                    ? MAX_RESULTS
                                    : maxResultsSet - startIndex))
                            .queryParam("startIndex", String.valueOf(startIndex))
                            .build());
            startIndex += MAX_RESULTS;

            GoogleBooksResponse response = httpClient.toBlocking().retrieve(req, GoogleBooksResponse.class);

            if (response != null && response.items() != null) {
                books.addAll(response.items().stream().map(GoogleBooksResponse::from).collect(Collectors.toSet()));
            } else {
                break;
            }

        } while (startIndex < maxResultsSet);
        return Optional.of(books);
    }
}
