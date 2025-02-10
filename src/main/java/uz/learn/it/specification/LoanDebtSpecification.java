package uz.learn.it.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;

public class LoanDebtSpecification {
    public static Specification<DailyLoanPaymentDebt> byClientId(Long clientId) {
        return (root, query, cb) -> {
            Join<DailyLoanPaymentDebt, Loan> loanJoin = root.join("loan");

            return cb.equal(loanJoin.get("client").get("id"), clientId);
        };
    }
}
