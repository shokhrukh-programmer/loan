package uz.learn.it.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.entity.User;

public class UserSpecification {
    public static Specification<User> getUserSpecification() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
}
