package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.Security.LoginAttemptService;
import com.hospetagem.hotel.service.ClienteService;
import com.hospetagem.hotel.service.FuncionarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam String tipoUsuario,
                        HttpSession session, HttpServletResponse httpServletResponse) {

        // Verificar se o usuário já está bloqueado
        if (loginAttemptService.isBlocked(email)) {
            return "Conta temporariamente bloqueada devido a múltiplas tentativas falhas. Tente novamente mais tarde.";
        }

        boolean autenticado = false;

        if ("cliente".equalsIgnoreCase(tipoUsuario)) {
            // Autentica Cliente
            autenticado = clienteService.autenticarCliente(email, senha);
        } else if ("funcionario".equalsIgnoreCase(tipoUsuario)) {
            // Autentica Funcionário
            autenticado = funcionarioService.autenticarFuncionario(email, senha);
        }

        if (autenticado) {
            // Sucesso: resetar tentativas e criar sessão
            loginAttemptService.loginSucceeded(email);
            session.setAttribute("usuario", email);
            session.setAttribute("tipoUsuario", tipoUsuario);
            return tipoUsuario + " autenticado com sucesso!";
        } else {
            // Falha: registrar tentativa
            loginAttemptService.loginFailed(email);
            return "Credenciais inválidas.";
        }
    }


    // Verifica quem está na sessão
    @GetMapping("/usuario")
    public String getUsuario(HttpSession session) {
        String usuario = (String) session.getAttribute("usuario");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        if (usuario == null || tipoUsuario == null) {
            return "Nenhum usuário autenticado.";
        }

        return "Usuário autenticado: " + usuario + " (Tipo: " + tipoUsuario + ")";
    }

    // Logout (encerra a sessão)
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "Sessão encerrada com sucesso!";
    }

}

