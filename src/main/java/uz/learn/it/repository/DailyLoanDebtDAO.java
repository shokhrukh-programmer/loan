package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.util.List;

@Repository
public interface DailyLoanDebtDAO extends JpaRepository<DailyLoanPaymentDebt, Long> {
    List<DailyLoanPaymentDebt> getDailyLoanPaymentDebtsByLoan_Id(long loanId);
}
