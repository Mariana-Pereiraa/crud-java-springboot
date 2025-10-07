package com.example.crud.controller;


import com.example.crud.dto.LoginRequestDTO;
import com.example.crud.dto.LoginResponseDTO;
import com.example.crud.dto.RegistroRequestDTO;
import com.example.crud.dto.UsuarioResponseDTO;
import com.example.crud.model.Papel;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.JwtService;
import com.example.crud.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin (origins = "http://localhost:5173")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        String token = jwtService.generateToken((Usuario) auth.getPrincipal());

        Usuario usuario = (Usuario) auth.getPrincipal();

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setPapel(usuario.getPapel());

        LoginResponseDTO userResponse = new LoginResponseDTO();
        userResponse.setAccessToken(token);
        userResponse.setExpiresIn(3600);
        userResponse.setUser(response);


        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequestDTO registroDTO){
        try{
            Usuario novoUsuario = usuarioService.registrar(registroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
