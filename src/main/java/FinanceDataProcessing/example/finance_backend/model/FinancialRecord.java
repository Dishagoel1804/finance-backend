package FinanceDataProcessing.example.finance_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "financial_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Type (INCOME/EXPENSE) is required")
    private String type; // Suggestion: Use an Enum here later

    @NotBlank(message = "Category is required")
    private String category;

    private LocalDate date;

    private String description;

    @Column(nullable = false)
    private boolean deleted = false;
}
