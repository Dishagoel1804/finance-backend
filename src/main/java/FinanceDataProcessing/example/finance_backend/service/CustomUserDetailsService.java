package FinanceDataProcessing.example.finance_backend.service;

import FinanceDataProcessing.example.finance_backend.model.User;
import FinanceDataProcessing.example.finance_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.DisabledException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if user is active (Requirement #1)
        if (!user.isActive()) {
            throw new DisabledException("User account is inactive");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Role from your DB (e.g., "ADMIN")
                .build();
    }
}