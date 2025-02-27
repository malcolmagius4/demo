package com.example.demo.infra.repository

import com.example.demo.common.model.Bet
import com.example.demo.common.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BetRepository : JpaRepository<Bet, Long> {
    fun findByPlayer(player: Player): Set<Bet>
}