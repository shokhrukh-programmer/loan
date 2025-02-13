package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.LoanPaymentHistory;

import java.util.List;

@Repository
public interface LoanPaymentHistoryDAO extends JpaRepository<LoanPaymentHistory, Long> , JpaSpecificationExecutor<LoanPaymentHistory> {
}
