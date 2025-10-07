package com.example.crud.service;

import com.example.crud.dto.RegistroRequestDTO;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuario registrar(RegistroRequestDTO registroDTO){
        if(usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario novaUsuario = new Usuario();
        novaUsuario.setEmail(registroDTO.getEmail());
        novaUsuario.setSenha(passwordEncoder.encode(registroDTO.getSenha()));
        novaUsuario.setPapel(registroDTO.getPapel());

        return usuarioRepository.save(novaUsuario);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}
