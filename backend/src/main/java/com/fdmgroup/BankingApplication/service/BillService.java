package com.fdmgroup.BankingApplication.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.BankingApplication.dto.BillDTO;
import com.fdmgroup.BankingApplication.model.BankAccount;
import com.fdmgroup.BankingApplication.model.Bill;
import com.fdmgroup.BankingApplication.model.CreditCard;
import com.fdmgroup.BankingApplication.model.CreditCardTransaction;
import com.fdmgroup.BankingApplication.repository.BillRepository;
import com.fdmgroup.BankingApplication.repository.CreditCardTransactionRepository;

@Service
public class BillService {

	@Autowired
	BillRepository billRepository;

	@Autowired
	CreditCardService creditCardService;

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	CreditCardTransactionRepository creditCardTransactionRepository;

	public void saveBills() {
		List<CreditCard> creditCards = creditCardService.getAllCreditCards();
		for (CreditCard creditCard : creditCards) {
			creditCard = creditCardService.payCreditWithCashback(creditCard);
			List<CreditCardTransaction> transactions = creditCardService
					.getTransactionsToBeBilledByCreditCard(creditCard);

			Bill bill = new Bill(
				LocalDate.now(), 
				LocalDate.now().plusDays(25),
				creditCard.getOutstandingBalance(), 
				toTwoDecimalPlaces(creditCard.getOutstandingBalance() * 0.02),
				0, 
				creditCard,
				transactions);

			transactions.stream().forEach(t -> {
				t.setBill(bill);
				creditCardTransactionRepository.save(t);
			});
			billRepository.save(bill);
		}
		return;
	}

	public List<Bill> getAllBills() {
		return billRepository.findAll();
	}

	public BillDTO getLatestBillByCreditCardId(long creditCardId, String username) {
		CreditCard creditCard = creditCardService.findCreditCardByIdAndUsername(creditCardId, username);
		return convertToDTO(billRepository.findFirstByCreditCardOrderByIssueDateDesc(creditCard));
	}

	public List<BillDTO> getBillsByCreditCardId(Long creditCardId, String username) {
		CreditCard creditCard = creditCardService.findCreditCardByIdAndUsername(creditCardId, username);
		return billRepository.findByCreditCardOrderByIssueDateDesc(creditCard).stream().map(b -> convertToDTO(b))
				.collect(Collectors.toList());
	}

	private BillDTO convertToDTO(Bill bill) {
		return new BillDTO(
					bill.getId(), 
					bill.getIssueDate(), 
					bill.getDueDate(), 
					bill.getBalanceDue(),
					bill.getMinimumPayment(), 
					bill.getTotalRepaymentAmount(),
					toTwoDecimalPlaces(bill.getBalanceDue() - bill.getTotalRepaymentAmount()), 
					bill.getBilledTransactions()
						.stream()
						.map(t -> creditCardService.convertToDTO(t)).collect(Collectors.toList())
		);
	}

	public void payBill(long creditCardId, String bankAccountNumber, double amount, String username) {
		CreditCard creditCard = creditCardService.findCreditCardByIdAndUsername(creditCardId, username);
		creditCardService.processTransaction(creditCardId, amount * -1, "Credit Payment", 0);

		BankAccount bankAccount = bankAccountService.findBankAccountByNumberAndUsername(bankAccountNumber, username);
		bankAccountService.processTransaction(bankAccount, amount * -1, "Credit Payment");

		Bill bill = billRepository.findFirstByCreditCardOrderByIssueDateDesc(creditCard);
		bill.setTotalRepaymentAmount(bill.getTotalRepaymentAmount() + amount);
		billRepository.save(bill);

	}

	private double toTwoDecimalPlaces(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

}
