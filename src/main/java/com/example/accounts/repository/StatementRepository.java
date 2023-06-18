package com.example.accounts.repository;

import com.example.accounts.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatementRepository extends JpaRepository<Statement, UUID> {

    List<Statement> findAllByAccountId(Integer accountId);

}
