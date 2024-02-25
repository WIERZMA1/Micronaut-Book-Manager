package org.book.manager.domain;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Serdeable
@Entity(name = "book")
public class Book {

    @Id
    @Column(name = "ISBN", nullable = false, unique = true)
    private String id;

    @Column(name = "title", length = 1024)
    private String title;

    @Column(name = "subtitle", length = 1024)
    private String subtitle;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publishedDate")
    private String publishedDate;

    @Column(name = "description", columnDefinition = "LONGVARCHAR")
    private String description;

    @Column(name = "pageCount")
    private int pageCount;

    @Column(name = "thumbnailURL")
    private String thumbnailUrl;

    @Column(name = "language")
    private String language;

    @Column(name = "previewLink")
    private String previewLink;

    @Column(name = "rating")
    private double rating;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "author_name"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Author> authors;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "books_categories",
            joinColumns = @JoinColumn(name = "category_name"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Category> categories;

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Book && this.id.equals(((Book) obj).id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = prime * result + id.hashCode();
        return result;
    }
}
