package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyLoanDebtDAO extends JpaRepository<DailyLoanPaymentDebt, Integer> {
    List<DailyLoanPaymentDebt> getByLoanId(long loanId);
//    void saveDailyLoanDebt(DailyLoanPaymentDebt debt);
//
//    List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId, int page, int size,
//                                                         LocalDate fromDate, LocalDate toDate);
}
