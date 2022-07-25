package repository;

import java.nio.file.Path;
import java.util.Collection;

public interface CriadorArquivo<T> {
    void criarArquivo(Collection<T> objects, Path path);
}
