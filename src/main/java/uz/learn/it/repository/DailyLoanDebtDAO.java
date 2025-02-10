package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.util.List;

@Repository
public interface DailyLoanDebtDAO extends JpaRepository<DailyLoanPaymentDebt, Integer>,
        JpaSpecificationExecutor<DailyLoanPaymentDebt> {
    List<DailyLoanPaymentDebt> getByLoanId(long loanId);
}
