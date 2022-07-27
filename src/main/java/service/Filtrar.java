package service;

import models.Filme;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public interface Filtrar<T> {
    public Collection<T> filtrar(Collection<T> collection, Predicate<T> predicate, Comparator<T> comparator, int quantidade);
}
