import models.Filme;
import repository.ManipularArquivosFilmes;
import service.FiltrarFilmes;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        ManipularArquivosFilmes manipularArquivosFilmes = new ManipularArquivosFilmes();

        Path[] caminhos = new Path[] {
                Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "movies1.csv"),
                Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "movies2.csv"),
                Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "movies3.csv")
        };

        ArrayList<Filme> listaFilmesEntrada = new ArrayList<>(manipularArquivosFilmes.lerArquivos(caminhos));

        FiltrarFilmes filtrarFilmes = new FiltrarFilmes();
        Predicate<Filme> predicateHorror = (filme) ->filme.getGenre().contains("Horror");
        Comparator<Filme> comparatorRating = Comparator.comparing(Filme::getRating).reversed();
        int qtdeFilmesHorror = 20;
        List<Filme> filmesHorror = new ArrayList<>(filtrarFilmes.filtrar(listaFilmesEntrada, predicateHorror, comparatorRating, qtdeFilmesHorror));

        Path caminhoFilmesHorror = Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "Top20Horror.txt");
        manipularArquivosFilmes.criarArquivo(filmesHorror, caminhoFilmesHorror);

        HashSet<Integer> anosFilmes = new HashSet<>(listaFilmesEntrada.stream()
                .map(filme -> filme.getYear().getValue())
                .collect(Collectors.toSet()));

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (Integer ano : anosFilmes ) {
            Predicate<Filme> predicate = (filme) -> filme.getYear().getValue() == ano;
            int qtdeFilmes = 50;
            Runnable runnable = () -> {
                Path caminhoFilmes = Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "Top50ano" + ano +".txt");
                List<Filme> filmes = new ArrayList<>(filtrarFilmes.filtrar(listaFilmesEntrada, predicate, comparatorRating, qtdeFilmes));
                manipularArquivosFilmes.criarArquivo(filmes, caminhoFilmes);
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
