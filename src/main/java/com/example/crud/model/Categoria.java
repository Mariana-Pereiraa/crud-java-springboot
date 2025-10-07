package com.example.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categorias", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nome")
})

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;

//    @OneToMany(mappedBy = "categoria")
//    @JsonIgnore
//    private List<Produto> produtos;

}
