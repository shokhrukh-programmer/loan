package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.TransactionHistory;

@Repository
public interface TransactionDAO extends JpaRepository<TransactionHistory, Integer> {
}
