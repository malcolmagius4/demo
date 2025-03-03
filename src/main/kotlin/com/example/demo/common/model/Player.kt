package com.example.demo.common.model

import com.example.demo.common.dto.LeaderboardPlayerDto
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "players")
data class Player(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val playerUuid: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val surname: String,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false, precision = 19, scale = 2)
    var walletBalance: BigDecimal = BigDecimal("1000.00"),

    @Column(nullable = false, precision = 19, scale = 2)
    var totalWinnings: BigDecimal = BigDecimal.ZERO,

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val transactions: Set<Transaction> = emptySet(),

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val bets: Set<Bet> = emptySet()
) {
    fun toLeaderboardDto(): LeaderboardPlayerDto {
        return LeaderboardPlayerDto(username = username, name = name, surname = surname, totalWinnings = totalWinnings)
    }
}