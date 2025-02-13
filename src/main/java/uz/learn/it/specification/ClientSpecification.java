package uz.learn.it.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.entity.Client;

public class ClientSpecification {
    public static Specification<Client> clients() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
}
