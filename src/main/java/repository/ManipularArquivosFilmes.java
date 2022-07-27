package repository;

import models.Filme;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Year;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ManipularArquivosFilmes implements ManipularArquivos<Filme> {

    @Override
    public Collection<Filme> lerArquivos(Path[] paths) {

        return Arrays.stream(paths)
                .parallel()
                .map(path -> {
                    try {
                        return Files.lines(path)
                                .skip(1)
                                .map(linha -> {
                                    String[] camposFilme = (linha).split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
                                    List<String> generos = List.of(camposFilme[2].replaceAll("\"", "").split(","));
                                    List<String> atores = List.of(camposFilme[5].replaceAll("\"", "").split(","));
                                    return new Filme(Integer.parseInt(camposFilme[0]),
                                            camposFilme[1],
                                            generos,
                                            camposFilme[3],
                                            camposFilme[4],
                                            atores,
                                            Year.parse(camposFilme[6]),
                                            Duration.ofMinutes(Long.parseLong(camposFilme[7])),
                                            /*Double.parseDouble(camposFilme[8])*/camposFilme[8].isEmpty() ? null: Double.parseDouble(camposFilme[8]),
                                            Integer.parseInt(camposFilme[9]),
                                            /*new BigDecimal(camposFilme[10]))*/camposFilme[10].isEmpty() ? null : BigDecimal.valueOf(Double.parseDouble(camposFilme[10])));
                                })
                                .collect(Collectors.toSet());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

    }

    @Override
    public void criarArquivo(Collection<Filme> objetos, Path destino) {
        List<String> linhasArquivo = objetos
                .stream()
                .parallel()
                .map(Filme::toString)
                .collect(Collectors.toList());

        try {
            Files.write(destino, linhasArquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
