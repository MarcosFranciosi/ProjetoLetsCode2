package oscar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuscaCsv {

    private final List<Oscar> listaOscar;

    public BuscaCsv(String nomeArquivo) {
        this.listaOscar = lerArquivo(nomeArquivo);
    }

    private List<Oscar> lerArquivo(String nomeArquivo) {
        try (Stream<String> fileLines = Files.lines(Paths.get(nomeArquivo))) {
            return fileLines
                    .skip(1)
                    .map(Oscar::aPartirDaLinha)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Oscar> getListaOscar() {
        return listaOscar;
    }

}
