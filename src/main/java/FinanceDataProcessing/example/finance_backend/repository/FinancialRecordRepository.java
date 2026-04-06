package FinanceDataProcessing.example.finance_backend.repository;

import FinanceDataProcessing.example.finance_backend.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    // Requirement #3: Updated to only sum active (non-deleted) records
    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type AND f.deleted = false")
    BigDecimal sumByType(@Param("type") String type);

    // Requirement #3: Updated to group only active records
    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.deleted = false GROUP BY f.category")
    List<Object[]> getCategoryTotals();

    // Find active records by type
    List<FinancialRecord> findByTypeAndDeletedFalse(String type);

    // Find active records by category
    List<FinancialRecord> findByCategoryAndDeletedFalse(String category);

    // Requirement #3: Find only the latest 5 active records
    List<FinancialRecord> findTop5ByDeletedFalseOrderByDateDesc();
}