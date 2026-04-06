package FinanceDataProcessing.example.finance_backend.service;

import FinanceDataProcessing.example.finance_backend.model.FinancialRecord;
import FinanceDataProcessing.example.finance_backend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private static final Logger logger = LoggerFactory.getLogger(FinanceService.class);
    private final FinancialRecordRepository repository;

    public FinancialRecord addRecord(FinancialRecord record) {
        // Ensure new records start as not deleted
        record.setDeleted(false);
        logger.info("Creating new financial record: {} for amount {}", record.getType(), record.getAmount());
        return repository.save(record);
    }

    // Requirement #3: Recent Activity (Latest 5 active records)
    public List<FinancialRecord> getRecentActivity() {
        return repository.findTop5ByDeletedFalseOrderByDateDesc();
    }

    // Requirement #2: View & Filter (Only showing active records)
    public List<FinancialRecord> getAllRecords(String type, String category) {
        if (type != null) return repository.findByTypeAndDeletedFalse(type);
        if (category != null) return repository.findByCategoryAndDeletedFalse(category);

        // Custom method to get all non-deleted records
        return repository.findAll().stream()
                .filter(r -> !r.isDeleted())
                .toList();
    }

    // Requirement #3: Category-wise Totals
    public List<Object[]> getCategoryStats() {
        return repository.getCategoryTotals();
    }

    // Requirement #2: Update record
    public FinancialRecord updateRecord(Long id, FinancialRecord newDetails) {
        logger.info("Updating record ID: {}", id);
        FinancialRecord record = repository.findById(id)
                .filter(r -> !r.isDeleted()) // Ensure we don't update a "deleted" record
                .orElseThrow(() -> new RuntimeException("Record not found or inactive"));

        record.setAmount(newDetails.getAmount());
        record.setType(newDetails.getType());
        record.setCategory(newDetails.getCategory());
        record.setDescription(newDetails.getDescription());
        record.setDate(newDetails.getDate());

        return repository.save(record);
    }

    // Requirement #2: SOFT DELETE
    public void deleteRecord(Long id) {
        logger.warn("Soft-deleting record with ID: {}", id);
        FinancialRecord record = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Delete failed: Record ID {} not found", id);
                    return new RuntimeException("Record not found");
                });
        record.setDeleted(true);
        repository.save(record);
    }

    // Requirement #3: Dashboard Summary (Aggregated Data)
    public Map<String, Object> getDashboardSummary() {
        // These now use the updated Repository methods that check for 'deleted = false'
        BigDecimal income = repository.sumByType("INCOME");
        BigDecimal expense = repository.sumByType("EXPENSE");

        income = (income != null) ? income : BigDecimal.ZERO;
        expense = (expense != null) ? expense : BigDecimal.ZERO;

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", income);
        summary.put("totalExpense", expense);
        summary.put("netBalance", income.subtract(expense));
        return summary;
    }
}