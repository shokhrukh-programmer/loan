package uz.learn.it.helper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CriteriaQueryUtil {
    public static List<Predicate> buildDatePredicates(
            CriteriaBuilder cb, Root<?> root, String dateField, LocalDate fromDate, LocalDate toDate) {
        List<Predicate> predicates = new ArrayList<>();

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(dateField), fromDate));
        }

        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(dateField), toDate));
        }

        return predicates;
    }
}
