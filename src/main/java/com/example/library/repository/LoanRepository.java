package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT l FROM Loan l JOIN FETCH l.client JOIN FETCH l.book")
    List<Loan> findAllWithClientAndBook();

    Page<Loan> findAll(Pageable pageable);

}