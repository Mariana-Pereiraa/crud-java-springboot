package com.example.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    private Double preco;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    @NotNull(message = "Categoria é obrigatória")
    private Categoria categoria;


    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer quantidadeEstoque = 0;


}
