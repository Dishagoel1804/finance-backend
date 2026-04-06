package FinanceDataProcessing.example.finance_backend.service;

import FinanceDataProcessing.example.finance_backend.model.User;
import FinanceDataProcessing.example.finance_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    public boolean authenticate(String username, String rawPassword) {
        // Correctly handle the Optional from the repository
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}