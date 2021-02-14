package br.com.altoalegremercado.aamuserapi.domain.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends  Person{

    private String cpf;
    private String cnpj;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> role;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
