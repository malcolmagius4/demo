package com.example.demo.infra.repository

import com.example.demo.common.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlayerRepository : JpaRepository<Player, Long> {
    fun findByPlayerUuid(playerUuid: UUID): Player?
    fun findByUsername(username: String): Player?
    fun existsPlayerByPlayerUuid(playerUuid: UUID): Boolean
    fun findTop10ByOrderByTotalWinningsDesc(): List<Player>
}