package repository;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface ManipularArquivos<T> {
    Collection<T> lerArquivos(Path[] paths);
    void criarArquivo(Collection<T> objetos, Path destino);
}
