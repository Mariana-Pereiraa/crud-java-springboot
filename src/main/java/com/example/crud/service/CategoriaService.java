package com.example.crud.service;

import com.example.crud.model.Categoria;
import com.example.crud.model.Produto;
import com.example.crud.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<Categoria> listarPaginado(Pageable pageable){
        return categoriaRepository.findAll(pageable);
    }

    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id){
        return categoriaRepository.findById(id);
    }

    public Categoria salvar(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
         categoriaRepository.deleteById(id);
    }

    public boolean existsByNome(String nome){
        return categoriaRepository.existsByNome(nome);
    }


}
