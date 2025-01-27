package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Loan;

@Repository
public interface LoanDAO extends JpaRepository<Loan, Long> {
    Loan getLoanById(long id);
}
