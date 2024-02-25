package org.book.manager.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.transaction.annotation.Transactional;
import org.book.manager.domain.Author;

import java.util.Optional;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findByNameIgnoreCase(String name);
}
