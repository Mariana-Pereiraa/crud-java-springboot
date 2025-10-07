package com.example.crud.service;

import com.example.crud.model.Produto;
import com.example.crud.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<Produto> listarPaginado(Pageable pageable){
        return produtoRepository.findAll(pageable);
    }

    public Optional<Produto> buscarPorId(Long id){
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void deletar(Long id){
        produtoRepository.deleteById(id);
    }

    public Produto atualizarEstoque(Long id, Integer novaQuantidade){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID" + id + "não encontrado"));

        if(novaQuantidade < 0){
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa");
        }
        produto.setQuantidade(novaQuantidade);
        return produtoRepository.save(produto);
    }
}
