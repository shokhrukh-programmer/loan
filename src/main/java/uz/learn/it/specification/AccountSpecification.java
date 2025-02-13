package uz.learn.it.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.entity.Account;

public class AccountSpecification {
    public static Specification<Account> getAccounts() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}
