package application;

import models.Filme;
import repository.ManipularArquivosFilmes;
import service.FiltrarFilmes;
import service.MedidorPerformance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        MedidorPerformance medidorPerformance = new MedidorPerformance();
        medidorPerformance.iniciar("Execução");
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

        for (Integer ano : anosFilmes) {
            Predicate<Filme> predicate = (filme) -> filme.getYear().getValue() == ano;
            int qtdeFilmes = 50;
            Runnable runnable = () -> {
                Path caminhoFilmes = Path.of(System.getProperty("user.dir") + "/src/main/resources/" + "Top50ano" + ano + ".txt");
                List<Filme> filmes = new ArrayList<>(filtrarFilmes.filtrar(listaFilmesEntrada, predicate, comparatorRating, qtdeFilmes));
                manipularArquivosFilmes.criarArquivo(filmes, caminhoFilmes);
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        medidorPerformance.terminar();

        medidorPerformance.mostraResultado();

        Path arquivoDesempenho = Path.of(System.getProperty("user.dir"), "src", "main", "resources", "desempenho.txt");
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
            Duration tempo = Duration.between(medidorPerformance.momentoInicio, medidorPerformance.momentoFim);
            Files.write(arquivoDesempenho, List.of(
                    "Inicio processamento: " + formatter.format(LocalDateTime.ofInstant(medidorPerformance.momentoInicio, ZoneId.systemDefault())),
                    "Fim processamento: " + formatter.format(LocalDateTime.ofInstant(medidorPerformance.momentoFim, ZoneId.systemDefault())),
                    String.format("Tempo em milissegundos: %.6f milissegundos", tempo.toNanos() / 1e6),
                    String.format("Tempo em segundos: %.3f segundos", tempo.toMillis() / 1e3)
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
