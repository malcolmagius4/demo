package com.example.demo.common.model

import jakarta.persistence.*
import java.util.UUID

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

    @Column(nullable = false)
    val walletBalance: Int = 1000,

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val transactions: Set<Transaction> = emptySet(),

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val bets: Set<Bet> = emptySet()
)