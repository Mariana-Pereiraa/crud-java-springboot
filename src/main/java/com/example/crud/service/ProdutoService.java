package com.example.crud.service;

import com.example.crud.model.Produto;
import com.example.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    //    para usar DataTableServer
//    public Page<Produto> listarPaginado(Pageable pageable){
//        return produtoRepository.findAll(pageable);
//    }

    public Optional<Produto> buscarPorId(Long id){
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void deletar(Long id){
        produtoRepository.deleteById(id);
    }
}
