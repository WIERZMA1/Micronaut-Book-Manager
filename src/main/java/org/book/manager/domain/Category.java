package org.book.manager.domain;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Serdeable
@Entity(name = "category")
public class Category {

    @Id
    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories", targetEntity = Book.class)
    @Column(name = "books")
    private Set<Book> booksList;

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof Category && this.name.equals(((Category) obj).name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = prime * result + name.hashCode();
        return result;
    }
}
