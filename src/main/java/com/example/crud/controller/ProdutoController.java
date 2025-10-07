package com.example.crud.controller;

import com.example.crud.model.Produto;
import com.example.crud.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Page<Produto> listarPaginado(Pageable pageable) {
        return produtoService.listarPaginado(pageable);
    }

    @PostMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        return produtoService.buscarPorId(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produto.getNome());
                    produtoExistente.setPreco(produto.getPreco());
                    produtoExistente.setQuantidade(produto.getQuantidade());
                    produtoExistente.setCategoria(produto.getCategoria());
                    Produto produtoAtualizado = produtoService.salvar(produtoExistente);
                    return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!produtoService.buscarPorId(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/{id}/estoque")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Produto> atualizarQuantidade(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Integer novaQuantidade = payload.get("quantidade");

        if (novaQuantidade == null) {
            return ResponseEntity.badRequest().build();
        }
        Produto produtoAtualizado = produtoService.atualizarEstoque(id, novaQuantidade);

        return ResponseEntity.ok(produtoAtualizado);

    }
}
