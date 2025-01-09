package whz.pti.eva.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whz.pti.eva.domain.Account;
import whz.pti.eva.repository.AccountRepository;
import whz.pti.eva.service.PaymentService;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final AccountRepository accountRepository;

    @Value("${payment.account.initial-balance:100.00}")
    private BigDecimal initialBalance;

    public PaymentServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean checkBalance(Long customerId, BigDecimal amount) {
        Account account = accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance().compareTo(amount) >= 0;
    }

    @Transactional
    public boolean chargeCustomer(Long customerId, BigDecimal amount) {
        Account account = accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for customer ID: " + customerId));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance for customer ID: " + customerId);
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        return true;
    }

    public Account createAccount(Long customerId) {
        if (accountRepository.findByCustomerId(customerId).isPresent()) {
            Account account = accountRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new IllegalArgumentException("No account found for customer ID: " + customerId));
            accountRepository.delete(account);
        }

        Account account = new Account(customerId, initialBalance);
        return accountRepository.save(account);
    }

    public Account getAccount(Long customerId) {
        return accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No account found for customer ID: " + customerId));
    }

    @Transactional
    public void addFunds(Long customerId, BigDecimal amount) {
        Account account = accountRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for customer ID: " + customerId));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}

