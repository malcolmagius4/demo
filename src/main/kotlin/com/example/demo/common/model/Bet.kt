package com.example.demo.common.model

import com.example.demo.common.dto.BetDto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "bets")
data class Bet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    val player: Player,

    @Column(nullable = false, precision = 19, scale = 2)
    val stakeAmount: BigDecimal,

    @Column(nullable = false, precision = 19, scale = 2)
    val winAmount: BigDecimal,

    @Column(nullable = false)
    val betValue: Int,

    @Column(nullable = false)
    val actualValue: Int,
) {
    fun toDto() : BetDto {
        return BetDto(stakeAmount = stakeAmount, winAmount = winAmount, betValue = betValue,
            actualValue = actualValue)
    }
}