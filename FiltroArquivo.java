
import java.nio.file.Path;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class FiltroArquivo {

    public Collection<Filme> filtrarArquivosAno(Path[] paths, Year year) {
        LerArquivosCsv lerArquivosCsv = new LerArquivosCsv();
        Collection<Filme> collectionFilme = lerArquivosCsv.lerArquivos((Path[]) paths);
        int quantidade = 50;
        return collectionFilme.stream()
                .filter(filme -> {
                    return filme.getYear().equals(year);
                })
                .sorted(Comparator.comparing(Filme::getRating))
                .limit(quantidade)
                .collect(Collectors.toSet());
    }

    public Collection<Filme> filtrarArquivosString(Path[] paths, String stringBusca) {
        LerArquivosCsv lerArquivosCsv = new LerArquivosCsv();
        Collection<Filme> collectionFilme = lerArquivosCsv.lerArquivos((Path[]) paths);
        int quantidade = 20;
        return collectionFilme.stream()
                .filter(filme -> {
                    return filme.getGenre().contains("Horror");
                })
                .sorted(Comparator.comparing(Filme::getRating))
                .limit(quantidade)
                .collect(Collectors.toSet());
    }

}