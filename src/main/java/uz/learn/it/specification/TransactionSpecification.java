package uz.learn.it.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.entity.TransactionHistory;

import java.time.LocalDate;

public class TransactionSpecification {
    public static Specification<TransactionHistory> byDateRange(LocalDate fromDate, LocalDate toDate) {
        return (root, query, builder) -> {
            if (fromDate != null && toDate != null) {
                return builder.between(root.get("date"), fromDate, toDate);
            } else if (fromDate != null) {
                return builder.greaterThanOrEqualTo(root.get("date"), fromDate);
            } else if (toDate != null) {
                return builder.lessThanOrEqualTo(root.get("date"), toDate);
            }

            return null;
        };
    }

    public static Specification<TransactionHistory> getTransactionHistoriesByClientId(long clientId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("client").get("id"), clientId));
    }
}
