//package uz.learn.it.repository;
//
//import uz.learn.it.dto.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//public class Storage {
//    private static long accountId = 1;
//    private static long clientId = 1;
//    private static long userCredentialId = 1;
//    private static long loanId = 1;
//    private static long historyId = 1;
//    private static long paymentId = 1;
//    public static final List<Client> clients = new ArrayList<>();
//    public static final List<Account> accounts = new ArrayList<>();
//    public static final List<UserCredentials> userCredentials = new ArrayList<>();
//    public static final List<TransactionHistory> operationHistories = new ArrayList<>();
//    public static final List<Loan> loans = new ArrayList<>();
//    public static final List<DailyLoanPaymentDebt> dailyLoanPaymentDebtList = new ArrayList<>();
//
//    public static void addClient(Client client) {
//        client.setId(clientId++);
//        clients.add(client);
//    }
//
//    public static void addAccount(Account account) {
//        account.setId(accountId++);
//        accounts.add(account);
//    }
//
//    public static void addUserLoginDetails(UserCredentials userCredential) {
//        userCredential.setId(userCredentialId++);
//        userCredentials.add(userCredential);
//    }
//
//    public static void addOperationToHistory(TransactionHistory transactionHistory) {
//        transactionHistory.setId(historyId++);
//        operationHistories.add(transactionHistory);
//    }
//
//    public static void addLoan(Loan loan) {
//        loan.setId(loanId++);
//        loans.add(loan);
//    }
//
//    public static void addToPaymentTable(DailyLoanPaymentDebt dailyLoanPaymentDebt) {
//        dailyLoanPaymentDebt.setId(paymentId++);
//        dailyLoanPaymentDebtList.add(dailyLoanPaymentDebt);
//    }
//
//    public static Optional<Client> findClientById(Long clientId) {
//        return clients
//                .stream()
//                .filter(client -> client.getId() == clientId)
//                .findFirst();
//    }
//
//    public static List<Account> getAccountsByClientId(Long clientId) {
//        return accounts.stream()
//                .filter(account -> account.getClientId() == clientId)
//                .collect(Collectors.toList());
//    }
//
//    public static Optional<Account> findAccountById(Long accountId) {
//        return accounts
//                .stream()
//                .filter(account -> account.getId() == accountId)
//                .findFirst();
//    }
//
//    public static List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
//        return dailyLoanPaymentDebtList.stream()
//                .filter(loan -> loan.getLoanId() == loanId)
//                .collect(Collectors.toList());
//    }
//
//    public static Optional<Loan> findLoanById(Long loanId) {
//        return loans
//                .stream()
//                .filter(loan -> loan.getId() == loanId)
//                .findFirst();
//    }
//
//    public static Optional<Account> findAccountByAccountNumber(String accountNumber) {
//        return accounts
//                .stream()
//                .filter(account -> account.getAccountNumber().equals(accountNumber))
//                .findFirst();
//    }
//}
