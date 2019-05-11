package com.example.makoto.testvolleysimplerequest;

public class usuario {

    String Email, Senha, Data, Quantidade, Nome, Refeicao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String quantidade) {
        Quantidade = quantidade;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getRefeicao() {
        return Refeicao;
    }

    public void setRefeicao(String refeicao) {
        Refeicao = refeicao;
    }

    @Override
    public String toString() {
        return getNome()+" | "+getData()+" | "+getEmail();
    }
}
