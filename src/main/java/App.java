import models.Filme;
import repository.ManipularArquivosFilmes;
import service.FiltrarFilmes;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class App {
    public static void main(String[] args) {
        ManipularArquivosFilmes manipularArquivosFilmes = new ManipularArquivosFilmes();

        Path[] caminhos = new Path[] {
                Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "movies1.csv"),
                Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "movies2.csv"),
                Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "movies3.csv")
        };

        manipularArquivosFilmes.lerArquivos(caminhos)
                .stream().findFirst().ifPresent(System.out::println);

        ArrayList<Filme> listaEntrada = new ArrayList<>(manipularArquivosFilmes.lerArquivos(caminhos));
        Predicate<Filme> predicate = (filme) -> filme.getYear().getValue() == 2012;
        Comparator<Filme> comparator = Comparator.comparing(Filme::getRating).reversed();
        int qtde = 5;
        FiltrarFilmes filtrarFilmes = new FiltrarFilmes();
        filtrarFilmes.filtrar(listaEntrada, predicate, comparator, qtde).forEach(System.out::println);
    }
}
