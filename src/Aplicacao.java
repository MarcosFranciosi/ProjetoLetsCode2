import oscar.BuscaCsv;
import oscar.Oscar;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;


public class Aplicacao {

    private static BuscaCsv novoArquivoAtor;
    private static BuscaCsv novoArquivoAtriz;

    public static void main(String[] args) {
        novoArquivoAtor = new BuscaCsv("oscar_age_male.csv");
        novoArquivoAtriz = new BuscaCsv("oscar_age_female.csv");
        atorMaisJovem();
        atrizMaisPremiada();
        atrizJovemMaisPremiada();
        vencedoresMaisDeUmOscar();
        resumoAtorAtriz();
    }


    private static void atorMaisJovem() {
        List<Oscar> oscarList = novoArquivoAtor.getListaOscar();

        System.out.println("===============================================================");
        System.out.println("Quem foi o Ator mais Jovem a ganhar um Oscar?");

        Optional<Oscar> atorJovem = oscarList.stream()
                .min(Comparator.comparingInt(Oscar::getIdade));
        atorJovem.ifPresent(it -> System.out.println(it.getNome()));

    }

    private static void atrizMaisPremiada() {
        List<Oscar> oscarList = novoArquivoAtriz.getListaOscar();

        System.out.println("===============================================================");
        System.out.println("Quem foi a Atriz mais premiada com Oscars?");

        Optional<Map.Entry<String, Long>> atrizMaisPremiada = oscarList.stream()
                .map(Oscar::getNome)
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue));
        atrizMaisPremiada.ifPresent(it -> System.out.println(it.getKey()));

    }

    private static void atrizJovemMaisPremiada() {
        List<Oscar> oscarList = novoArquivoAtriz.getListaOscar();

        System.out.println("===============================================================");
        System.out.println("Qual atriz entre 20 e 30 anos que mais vezes foi vencedora?");

        Optional<Map.Entry<String, Long>> atrizJovemMaisVencedora = oscarList.stream()
                .filter((Oscar o) -> o.getIdade() >= 20 && o.getIdade() <= 30)
                .map(Oscar::getNome)
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue));
        atrizJovemMaisVencedora.ifPresent(it -> System.out.println(it.getKey()));
    }

    private static void vencedoresMaisDeUmOscar() {
        List<Oscar> oscarList = Stream.concat(
                 novoArquivoAtor.getListaOscar().stream(),
                 novoArquivoAtriz.getListaOscar().stream()
        ).collect(Collectors.toList());

        System.out.println("===============================================================");
        System.out.println("Quais atores ou atrizes receberam mais de um Oscar?");
        System.out.println("");
        oscarList.stream()
                .map(Oscar::getNome)
                .collect(groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter((Map.Entry<String, Long> m) -> m.getValue() > 1)
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()))
                .forEach(it -> System.out.println(it.getKey()));

    }

    private static void resumoAtorAtriz() {

        System.out.println("===============================================================");
        System.out.println("Informe o nome do Ator: ");
        Scanner ler = new Scanner(System.in);
        String nome = ler.next().trim();

        String pattern = nome.isEmpty() ? "" : String.format("(?i).*\\b(%s)\\b.*", nome);

        List<Oscar> oscarList = Stream.concat(
                novoArquivoAtor.getListaOscar().stream(),
                novoArquivoAtriz.getListaOscar().stream()
        ).collect(Collectors.toList());

        List<Oscar> filtro = oscarList.stream()
                .filter((Oscar o) -> o.getNome().matches(pattern))
                .collect(Collectors.toList());

        if(!filtro.isEmpty()) {
            filtro.stream()
                    .collect(groupingBy(Oscar::getNome))
                    .forEach((key, oscar) -> {
                        System.out.printf("\nNome: %s (Total: %d)\n", key, oscar.size());
                        oscar.forEach(it -> System.out.println("Filme Premiado: " + it.getFilme()));
                    });
        } else {
            System.out.println("Nenhum premio encontrado para este Ator/Atriz");
        }

    }
}
