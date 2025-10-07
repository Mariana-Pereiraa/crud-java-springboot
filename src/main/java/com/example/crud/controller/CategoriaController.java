package com.example.crud.controller;


import com.example.crud.model.Categoria;
import com.example.crud.model.Produto;
import com.example.crud.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Page<Categoria> listarPaginado(Pageable pageable){
        return categoriaService.listarPaginado(pageable);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public List<Categoria> listarTodos() {
        return categoriaService.listarTodos();
    }

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> criar(@Valid @RequestBody Categoria categoria) {
        if(categoriaService.existsByNome(categoria.getNome())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe uma categoria com esse nome");
        }
        try {
            Categoria salvo = categoriaService.salvar(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de integridade: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
        return categoriaService.buscarPorId(id).map(existing -> {
            existing.setNome(categoria.getNome());
            try {
                Categoria atualizado = categoriaService.salvar(existing);
                return ResponseEntity.ok(atualizado);
            } catch (DataIntegrityViolationException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome duplicado");
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> deletar (@PathVariable Long id){
        if(!categoriaService.buscarPorId(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
