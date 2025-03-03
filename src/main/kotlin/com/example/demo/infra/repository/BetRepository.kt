package com.example.demo.infra.repository

import com.example.demo.common.model.Bet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BetRepository : JpaRepository<Bet, Long> {
    fun findByPlayer_PlayerUuid(playerUuid: UUID): List<Bet>
}