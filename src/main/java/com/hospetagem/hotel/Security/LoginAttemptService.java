package com.hospetagem.hotel.Security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5; // Máximo de tentativas
    private static final long LOCK_TIME_MINUTES = 10; // Tempo de bloqueio em minutos

    private final Map<String, LoginAttempt> attemptsCache = new HashMap<>();

    public void loginFailed(String email) {
        LoginAttempt attempt = attemptsCache.getOrDefault(email,
                new LoginAttempt(email, 0, LocalDateTime.now()));

        attempt.setAttempts(attempt.getAttempts() + 1);
        attempt.setLastAttempt(LocalDateTime.now());
        attemptsCache.put(email, attempt);
    }

    public boolean isBlocked(String email) {
        LoginAttempt attempt = attemptsCache.get(email);
        if (attempt == null) {
            return false;
        }

        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            LocalDateTime lockEndTime = attempt.getLastAttempt().plusMinutes(LOCK_TIME_MINUTES);
            if (LocalDateTime.now().isBefore(lockEndTime)) {
                return true; // Ainda bloqueado
            } else {
                // Expirou o tempo de bloqueio, limpar registro
                attemptsCache.remove(email);
            }
        }
        return false; // Não está bloqueado
    }

    public void loginSucceeded(String email) {
        // Remove as tentativas após um login bem-sucedido
        attemptsCache.remove(email);
    }

    // Classe interna para rastrear a tentativa de login
    private static class LoginAttempt {
        private final String email;
        private int attempts;
        private LocalDateTime lastAttempt;

        public LoginAttempt(String email, int attempts, LocalDateTime lastAttempt) {
            this.email = email;
            this.attempts = attempts;
            this.lastAttempt = lastAttempt;
        }

        public int getAttempts() {
            return attempts;
        }

        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }

        public LocalDateTime getLastAttempt() {
            return lastAttempt;
        }

        public void setLastAttempt(LocalDateTime lastAttempt) {
            this.lastAttempt = lastAttempt;
        }
    }
}