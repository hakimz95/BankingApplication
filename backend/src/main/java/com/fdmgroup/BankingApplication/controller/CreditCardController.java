package com.fdmgroup.BankingApplication.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.BankingApplication.BankingApplication;
import com.fdmgroup.BankingApplication.dto.CreditCardRequestDTO;
import com.fdmgroup.BankingApplication.dto.CreditCardTransactionDTO;
import com.fdmgroup.BankingApplication.dto.PurchaseRequestDTO;
import com.fdmgroup.BankingApplication.model.CreditCard;
import com.fdmgroup.BankingApplication.security.UserPrincipal;
import com.fdmgroup.BankingApplication.service.CreditCardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/credit-cards")
public class CreditCardController {
	private static final Logger LOGGER = LogManager.getLogger(BankingApplication.class);
	
    @Autowired
    private CreditCardService creditCardService;

	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/apply")
	public ResponseEntity<?> applyCreditCard(@Valid @RequestBody CreditCardRequestDTO req, @AuthenticationPrincipal UserPrincipal currentUser) {
		CreditCard savedCreditCard = creditCardService.createCreditCard(req.getAnnualSalary(), req.getCardType(), currentUser.getUsername());
		LOGGER.info("CreditCardController: Apply Credit Card request recieved for user: {}, {}", currentUser.getId(), currentUser.getUsername());
		return new ResponseEntity<>(savedCreditCard, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/me")
	public List<CreditCard> getCreditCardsOfCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser) {
		LOGGER.info("CreditCardController: Get Credit Cards of Current User request with user Id: {}, {}", currentUser.getId(), currentUser.getUsername());
		return creditCardService.getCreditCardsByUsername(currentUser.getUsername());
    }

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/{creditCardId}")
	public ResponseEntity<?> getSingleCreditCardOfCurrentUser(@PathVariable("creditCardId") Long id, @AuthenticationPrincipal UserPrincipal currentUser) {
		LOGGER.info("CreditCardController: Get Single Credit Card request received with User: {}, {}, and CreditCardID: {}", currentUser.getId(), currentUser.getUsername(),id);
		CreditCard creditCard = creditCardService.findCreditCardByIdAndUsername(id, currentUser.getUsername());
		return new ResponseEntity<>(creditCard, HttpStatus.OK);
	}

	@PostMapping("/purchase")
	public ResponseEntity<?> purchase(@Valid @RequestBody PurchaseRequestDTO req) {
		CreditCardTransactionDTO savedTransaction = creditCardService.purchase(req.creditCardId(), req.amount(), req.merchantName(), req.mcc());
		LOGGER.info("CreditCardController: Purchase request received with body: {}", req.toString());
		return new ResponseEntity<>(savedTransaction, HttpStatus.ACCEPTED);
	}

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{creditCardId}/history")
    public ResponseEntity<?> getTransactionHistory(@PathVariable("creditCardId") Long id, 
            @RequestParam(required = false) Integer month, 
            @RequestParam(required = false) Integer year, 
            @AuthenticationPrincipal UserPrincipal currentUser) {

        List<CreditCardTransactionDTO> history;
        if (year != null) {
            if (month != null) {
                history = creditCardService.getTransactionsByMonthAndYear(id, month, year, currentUser.getUsername());
                LOGGER.info("CreditCardController: Get Transaction History request received for Credit Card ID: {}, month: {}, year: {}, userId: {}", id, month, year, currentUser.getId());
            } else {
                history = creditCardService.getTransactionsByYear(id, year, currentUser.getUsername());
                LOGGER.info("CreditCardController: Get Transaction History request received for Credit Card ID: {}, year: {}, userId: {}", id, year, currentUser.getId());
            }
        } else {
            history = creditCardService.getTransactionsByIdAndUsername(id, currentUser.getUsername());
            LOGGER.info("CreditCardController: Get Transaction History request received for Credit Card ID: {}, userId: {}", id, currentUser.getId());
        }
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/late-charge")
    public  ResponseEntity<?> latePaymentCharge() {
        creditCardService.generateLatePaymentCharge();
        return ResponseEntity.ok("Successfully generated late payment charges");
    }
}
