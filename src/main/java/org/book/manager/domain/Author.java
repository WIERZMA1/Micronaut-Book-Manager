package org.book.manager.domain;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Serdeable
@Entity(name = "author")
public class Author {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authors", targetEntity = Book.class)
    @Column(name = "books")
    private Set<Book> booksList;

    @Column(name = "rating")
    private double rating;

    public Author(String name, double rating) {
        this.name = name;
        DecimalFormat df = new DecimalFormat("#.##");
        rating = Double.isNaN(rating) ? 0.0 : rating;
        this.rating = this.rating == 0.0 && rating != 0.0
                ? rating
                : Double.parseDouble(df.format(this.rating + rating / 2));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Author && this.name.equals(((Author) obj).name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = prime * result + name.hashCode();
        return result;
    }
}
