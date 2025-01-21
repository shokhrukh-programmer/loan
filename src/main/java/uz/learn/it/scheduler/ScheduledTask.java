package uz.learn.it.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.learn.it.service.LoanService;

@Component
public class ScheduledTask {
    private final LoanService loanService;

    @Autowired
    public ScheduledTask(LoanService loanService) {
        this.loanService = loanService;
    }

    //@Scheduled(cron = "0 0 0 * * ?") // Runs at midnight daily
    @Scheduled(cron = "*/5 * * * * ?")
    public void writeDailyInterest() {
        loanService.calculateAndWriteInterest();
    }
}
