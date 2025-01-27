package uz.learn.it.repository;

import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.util.List;

public interface DailyLoanDebtDAO {
    void saveDailyLoanDebt(DailyLoanPaymentDebt debt);

    List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId);
}
