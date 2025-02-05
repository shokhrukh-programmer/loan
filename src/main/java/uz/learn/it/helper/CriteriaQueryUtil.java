package uz.learn.it.helper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CriteriaQueryUtil {
    public static List<Predicate> buildDatePredicates(
            CriteriaBuilder cb, Root<?> root, String dateField, LocalDate fromDate, LocalDate toDate) {
        List<Predicate> predicates = new ArrayList<>();

        Expression<LocalDate> dateExpression = cb.function("TO_DATE", LocalDate.class,
                root.get(dateField), cb.literal("YYYY-MM-DD"));

        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(dateExpression, fromDate));
        }

        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(dateExpression, toDate));
        }

        return predicates;
    }
}
