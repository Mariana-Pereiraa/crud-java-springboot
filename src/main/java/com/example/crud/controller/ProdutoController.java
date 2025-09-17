package com.example.crud.controller;

import com.example.crud.model.Produto;
import com.example.crud.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarTodos(){
        return produtoService.listarTodos();
    }

//    @GetMapping
//    public Page<Produto> listarPaginado(Pageable pageable){
//        return produtoService.listarPaginado(pageable);
//    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto){
        Produto produtoSalvo = produtoService.salvar(produto);
        return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto){
        return produtoService.buscarPorId(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produto.getNome());
                    produtoExistente.setPreco(produto.getPreco());
                    Produto produtoAtualizado = produtoService.salvar(produtoExistente);
                    return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if (!produtoService.buscarPorId(id).isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
