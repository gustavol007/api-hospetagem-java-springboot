package com.hospetagem.hotel.controllers;

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

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam String tipoUsuario,
                        HttpSession session, SessionStatus sessionStatus, HttpServletResponse httpServletResponse) {
        if ("cliente".equalsIgnoreCase(tipoUsuario)) {
            // Autentica Cliente
            if (clienteService.autenticarCliente(email, senha)) {

                session.setAttribute("usuario", email);
                session.setAttribute("tipoUsuario", "cliente");
                return "Cliente autenticado com sucesso!";
            }
        } else if ("funcionario".equalsIgnoreCase(tipoUsuario)) {
            // Autentica Funcionário
            if (funcionarioService.autenticarFuncionario(email, senha)) {
                session.setAttribute("usuario", email);
                session.setAttribute("tipoUsuario", "funcionario");
                return "Funcionário autenticado com sucesso!";
            }
        }

        return "Credenciais inválidas ou tipo de usuário não reconhecido.";
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

