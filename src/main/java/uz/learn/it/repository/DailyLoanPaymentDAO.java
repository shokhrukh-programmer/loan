package uz.learn.it.repository;

import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.util.List;

public interface DailyLoanPaymentDAO {
    void saveDailyPayment(DailyLoanPaymentDebt dailyLoanPaymentDebt);
    List<DailyLoanPaymentDebt> getDailyPaymentsByLoanId(long loanId);
}
