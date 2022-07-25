package repository;

import java.nio.file.Path;
import java.util.Collection;

public interface ManipularArquivos<T> {
    Collection<T> lerArquivos(Path[] paths);

    default void criarArquivo(Collection<T> objects, Path path) {
        getCriadorArquivo().criarArquivo(objects, path);
    }

    CriadorArquivo<T> getCriadorArquivo();
}
