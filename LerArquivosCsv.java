import repository.CriadorArquivo;
import repository.ManipularArquivos;

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

public class LerArquivosCsv implements ManipularArquivos {

    @Override
    public Collection<Filme> lerArquivos(Path[] paths) {

        return Arrays.stream(paths)
                .parallel()
                .map(arquivo -> {
                    try {
                        return Files.lines(arquivo)
                                .map(linha -> {
                                    String[] camposFilme = (linha).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                                    List<String> generos = List.of(camposFilme[2].split(","));
                                    List<String> atores = List.of(camposFilme[5].split(","));
                                    return new Filme(Integer.parseInt(camposFilme[0]),
                                            camposFilme[1],
                                            generos,
                                            camposFilme[3],
                                            camposFilme[4],
                                            atores,
                                            Year.parse(camposFilme[6]),
                                            LocalTime.parse(camposFilme[7]),
                                            Float.parseFloat(camposFilme[8]),
                                            Integer.parseInt(camposFilme[9]),
                                            new BigDecimal(camposFilme[10]));
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
    public CriadorArquivo getCriadorArquivo() {
        return null;
    }
}
