package whz.pti.eva.boundary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whz.pti.eva.domain.Account;
import whz.pti.eva.domain.request.PaymentRequest;
import whz.pti.eva.service.PaymentService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/balance/check")
    public ResponseEntity<Boolean> checkBalance(@RequestBody PaymentRequest request) {
        boolean result = paymentService.checkBalance(request.getCustomerId(), request.getAmount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> processPayment(@RequestBody PaymentRequest request) {
        boolean result = paymentService.chargeCustomer(request.getCustomerId(), request.getAmount());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/accounts/create")
    public ResponseEntity<Account> createAccount(@RequestParam Long customerId) {
        try {
            Account account = paymentService.createAccount(customerId);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/accounts/{customerId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long customerId) {
        try {
            Account account = paymentService.getAccount(customerId);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addFunds")
    public ResponseEntity<String> addFunds(@RequestBody PaymentRequest request) {
        try {
            paymentService.addFunds(request.getCustomerId(), request.getAmount());
            return ResponseEntity.ok("Balance updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update balance: " + e.getMessage());
        }
    }


}
