package uz.learn.it.repository;

import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.time.LocalDate;
import java.util.List;

public interface DailyLoanDebtDAO {
    void saveDailyLoanDebt(DailyLoanPaymentDebt debt);

    List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId, int page, int size,
                                                         LocalDate fromDate, LocalDate toDate);
}
