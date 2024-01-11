package com.fdmgroup.BankingApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.BankingApplication.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}
