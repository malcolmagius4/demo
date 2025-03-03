package com.example.demo.infra.repository

import com.example.demo.common.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByPlayer_PlayerUuid(playerUuid: UUID): List<Transaction>
}