package oscar;

import oscar.exceptions.IdentificadorNullException;
import oscar.exceptions.PeriodoException;

public class Oscar {
    private String filme;
    private String nome;
    private int idade;
    private int ano;

    public Oscar(int ano, int idade, String nome, String filme) {
        this.setAno(ano);
        this.setIdade(idade);
        this.setNome(nome);
        this.setFilme(filme);
    }

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        if(filme == null || filme.equals("")) throw  new IdentificadorNullException();
        this.filme = filme;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.equals("")) throw  new IdentificadorNullException();
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if(idade < 0) throw new PeriodoException(idade);
        this.idade = idade;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        if(ano < 0) throw new PeriodoException(ano);
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Oscar{" +
                "filme='" + filme + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", ano=" + ano +
                '}';
    }

    public static Oscar aPartirDaLinha(String linha) {
        String[] campos = linha.split("[;\\.,\\t\\|](\\s)*");

        return new Oscar(
                Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                campos[3],
                campos[4]
        );
    }

}
