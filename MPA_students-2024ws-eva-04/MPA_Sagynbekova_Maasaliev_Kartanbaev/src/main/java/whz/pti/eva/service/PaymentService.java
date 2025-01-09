package whz.pti.eva.service;

import whz.pti.eva.domain.Account;

import java.math.BigDecimal;

public interface PaymentService {
    boolean checkBalance(Long customerId, BigDecimal amount);
    boolean chargeCustomer(Long customerId, BigDecimal amount);
    Account createAccount(Long customerId);
    Account getAccount(Long customerId);
    void addFunds(Long customerId, BigDecimal amount);
}
