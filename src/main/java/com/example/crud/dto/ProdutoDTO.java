//package com.example.crud.dto;
//
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//import lombok.Data;
//
//@Data
//public class ProdutoDTO {
//    private Long id;
//
//    @NotBlank(message =  "o nome do produto é obrigatório")
//    private String nome;
//
//    @NotNull(message = "o preço do produto é obrigatório")
//    @Positive(message = "o preço deve ser maior que zero")
//    private Double preco;
//
//    private Integer quantidadeEstoque;
//
//
//    @NotNull(message = "a categoria do produto é obrigatória")
//    private Long categoriaId;
//
//    private String categoriaNome;
//}
