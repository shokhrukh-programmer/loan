package uz.learn.it.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;

@Component
public class ScheduledTask {
    private final TransactionService transactionService;

    @Autowired
    public ScheduledTask(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //@Scheduled(cron = "0 0 0 * * ?") // Runs at midnight daily
    @Scheduled(cron = "*/5 * * * * ?")
    public void writeDailyInterest() {
        transactionService.calculateAndWriteInterest();
    }
}
