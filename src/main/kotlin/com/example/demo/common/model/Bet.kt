package com.example.demo.common.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "bets")
data class Bet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    val player: Player,

    @Column(nullable = false)
    val stakeAmount: Int,

    @Column(nullable = false)
    val winAmount: Int,

    @Column(nullable = false)
    val betValue: Int,

    @Column(nullable = false)
    val timestamp: Instant
)