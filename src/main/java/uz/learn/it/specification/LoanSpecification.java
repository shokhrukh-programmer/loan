package uz.learn.it.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;

public class LoanSpecification {
    public static Specification<Loan> getLoans() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<DailyLoanPaymentDebt> getDailyLoanPaymentsById(long loanId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("loan").get("id"), loanId);
    }

    public static Specification<DailyLoanPaymentDebt> getDailyLoanPaymentsByClientId(long clientId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("loan").get("client").get("id"), clientId);
    }

    public static Specification<LoanPaymentHistory> getLoanPaymentHistory() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<Loan> getLoansByClientId(long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("client").get("id"), id);
    }

    public static Specification<LoanPaymentHistory> getLoanPaymentHistoryByClientId(long clientId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("loan").join("client").get("id"), clientId);
    }
}
