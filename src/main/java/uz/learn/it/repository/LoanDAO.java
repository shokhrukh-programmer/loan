package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Loan;

import java.util.Optional;

@Repository
public interface LoanDAO extends JpaRepository<Loan, Long> {
    Optional<Loan> getLoanById(long id);
}
