package repository;

import models.Filme;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.Year;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ManipularArquivosFilmes implements ManipularArquivos<Filme> {

    @Override
    public Collection<Filme> lerArquivos(Path[] paths) {
        Arrays.stream(paths)
                .parallel()
                .map(path -> {
                    try {
                        Files.lines(path)
                                .skip(1)
                                .forEach(linha -> {
                                    String[] tokens = linha.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                                    List<String> genero = List.of(tokens[2].replaceAll("\"", "").split(","));
                                    List<String> atores = List.of(tokens[5].replaceAll("\"", "").split(","));
                                    LocalTime duracaoFilme = LocalTime.parse(tokens[7]);
                                    new Filme(Integer.parseInt(tokens[0]), tokens[1], genero, tokens[3], tokens[4], atores,
                                            Year.parse(tokens[6]), duracaoFilme, Double.parseDouble(tokens[8]), Integer.parseInt(tokens[9]),
                                            BigDecimal.valueOf(Double.parseDouble(tokens[10])));
                                });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }).collect(Collectors.toList());
        return null;
    }

    @Override
    public void criarArquivo(Collection<Filme> objetos, Path destino) {
        List<String> linhasArquivo = objetos
                .parallelStream()
                .map(Filme::toString)
                .collect(Collectors.toList());

        try {
            Files.write(destino, linhasArquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
