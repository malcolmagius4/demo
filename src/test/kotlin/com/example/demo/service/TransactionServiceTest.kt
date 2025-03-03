package com.example.demo.service

import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.enum.TransactionType
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Player
import com.example.demo.common.model.Transaction
import com.example.demo.infra.repository.PlayerRepository
import com.example.demo.infra.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockitoExtension::class)
class TransactionServiceTest {

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @Mock
    private lateinit var playerService: PlayerService

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @InjectMocks
    private lateinit var transactionService: TransactionService

    @Test
    fun incoming_transaction_should_increase_wallet_balance_and_return_transaction() {
        //given
        val player = Player(playerUuid = UUID.randomUUID(), name = "John", surname = "Doe", username = "john123",
            walletBalance = BigDecimal("1000.00"), totalWinnings = BigDecimal.ZERO)
        val amount = BigDecimal("200.00")
        val transaction = Transaction(amount = amount, type = TransactionType.IN, player = player)

        //when
        whenever(transactionRepository.save(transaction))
            .thenReturn(transaction)
        whenever(playerRepository.save(player)).thenReturn(player)

        val result = transactionService.incomingTransaction(amount, player)

        //then
        assertEquals(player.walletBalance, BigDecimal("1200.00"))
        assertEquals(player.totalWinnings, BigDecimal("200.00"))
        assertEquals(TransactionType.IN, result.type)
        assertEquals(amount, result.amount)
    }

    @Test
    fun outgoing_transaction_should_decrease_wallet_balance_and_return_transaction() {
        //given
        val player = Player(playerUuid = UUID.randomUUID(), name = "Jane", surname = "Doe", username = "jane123",
            walletBalance = BigDecimal("500.00"), totalWinnings = BigDecimal.ZERO)
        val amount = BigDecimal("100.00")
        val transaction = Transaction(amount = amount, type = TransactionType.OUT, player = player)

        //when
        whenever(transactionRepository.save(transaction))
            .thenReturn(transaction)
        whenever(playerRepository.save(player)).thenReturn(player)

        val result = transactionService.outgoingTransaction(amount, player)

        //then
        assertEquals(player.walletBalance, BigDecimal("400.00"))
        assertEquals(TransactionType.OUT, result.type)
        assertEquals(amount, result.amount)
    }

    @Test
    fun get_transactions_should_return_transactions_when_player_exists() {
        //given
        val playerUuid = UUID.randomUUID()
        val player = Player(playerUuid = playerUuid, name = "Player", surname = "One", username = "playerone",
            walletBalance = BigDecimal("1000.00"), totalWinnings = BigDecimal.ZERO)
        val transactions = listOf(Transaction(amount = BigDecimal("50.00"), type = TransactionType.IN, player = player))

        //when
        whenever(playerService.playerExists(playerUuid)).thenReturn(true)
        whenever(transactionRepository.findByPlayer_PlayerUuid(playerUuid)).thenReturn(transactions)

        val result = transactionService.getTransactions(playerUuid)

        //then
        assertEquals(1, result.size)
        assertEquals(TransactionType.IN, result[0].type)
    }

    @Test
    fun get_transactions_should_throw_exception_when_player_does_not_exist() {
        //given
        val playerUuid = UUID.randomUUID()

        //when
        whenever(playerService.playerExists(playerUuid)).thenReturn(false)

        //then
        val exception = assertThrows<BusinessRuntimeException> {
            transactionService.getTransactions(playerUuid)
        }

        assertEquals(ErrorCode.PLAYER_UUID_NOT_FOUND, exception.errorCode)
        assertEquals(404, exception.httpStatus)
    }
}
