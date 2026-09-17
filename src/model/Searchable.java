package model;

import java.util.List;

/**
 * Interface to define search behavior.
 */
public interface Searchable<T> {
    List<T> searchByTitle(String title);
    List<T> searchByAuthor(String author);
}
