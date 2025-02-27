package com.example.demo.common.model

import com.example.demo.common.dto.TransactionDto
import com.example.demo.common.enum.TransactionType
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val amount: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: TransactionType,

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    val player: Player,

    @Column(nullable = false)
    val timestamp: Instant
) {
    fun toDto() : TransactionDto {
        return TransactionDto(amount = amount, type = type, timestamp = timestamp)
    }
}