package FinanceDataProcessing.example.finance_backend.controller;

import FinanceDataProcessing.example.finance_backend.model.User;
import FinanceDataProcessing.example.finance_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')") // Requirement: Only Admin can manage status
    public ResponseEntity<User> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleUserStatus(id));
    }
}