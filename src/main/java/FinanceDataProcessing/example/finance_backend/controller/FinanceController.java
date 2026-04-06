package FinanceDataProcessing.example.finance_backend.controller;

import FinanceDataProcessing.example.finance_backend.model.FinancialRecord;
import FinanceDataProcessing.example.finance_backend.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService service;

    // Requirement #2: Create record
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinancialRecord> add(@RequestBody FinancialRecord record) {
        return ResponseEntity.ok(service.addRecord(record));
    }

    // Requirement #3: Dashboard Summary (Aggregated Data)
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER', 'ANALYST')")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return ResponseEntity.ok(service.getDashboardSummary());
    }

    // Requirement #2: View & Filter records
    @GetMapping("/records")
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER', 'ANALYST')")
    public ResponseEntity<List<FinancialRecord>> getRecords(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(service.getAllRecords(type, category));
    }

    @GetMapping("/recent")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public ResponseEntity<List<FinancialRecord>> getRecent() {
        return ResponseEntity.ok(service.getRecentActivity()); // Ensure this is in your Service too
    }

    // Requirement #2: Update record
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FinancialRecord> updateRecord(@PathVariable Long id, @RequestBody FinancialRecord record) {
        return ResponseEntity.ok(service.updateRecord(id, record));
    }

    // Requirement #2: Delete record
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRecord(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.ok("Record deleted successfully");
    }

    // Requirement #3: Category-wise totals
    @GetMapping("/stats/categories")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public ResponseEntity<List<Object[]>> getCategoryStats() {
        return ResponseEntity.ok(service.getCategoryStats());
    }
}