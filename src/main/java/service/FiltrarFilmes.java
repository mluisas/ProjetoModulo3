package service;

import models.Filme;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FiltrarFilmes implements Filtrar<Filme> {

    public Collection<Filme> filtrar(Collection<Filme> collectionFilme, Predicate<Filme> predicate, Comparator<Filme> comparator, int quantidade) {

        return collectionFilme.stream()
                .filter(predicate)
                .sorted(comparator)
                .limit(quantidade)
                .collect(Collectors.toList());
    }
}
