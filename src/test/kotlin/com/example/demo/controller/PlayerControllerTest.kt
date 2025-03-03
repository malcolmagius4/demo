package com.example.demo.controller

import com.example.demo.common.dto.*
import com.example.demo.common.enum.TransactionType
import com.example.demo.common.model.Player
import com.example.demo.common.model.Transaction
import com.example.demo.contoller.PlayerController
import com.example.demo.service.PlayerService
import com.example.demo.service.TransactionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class PlayerControllerTest {

    @Mock
    private lateinit var playerService: PlayerService

    @Mock
    private lateinit var transactionService: TransactionService

    @InjectMocks
    private lateinit var playerController: PlayerController

    private lateinit var playerUuid: UUID
    private lateinit var player: Player
    private lateinit var registrationRequestDto: RegistrationRequestDto
    private lateinit var transaction: Transaction
    private lateinit var transactionDto: TransactionDto

    @BeforeEach
    fun setUp() {
        playerUuid = UUID.randomUUID()

        player = Player(
            playerUuid = playerUuid,
            name = "John",
            surname = "Doe",
            username = "johndoe",
            walletBalance = BigDecimal("1000.00")
        )

        registrationRequestDto = RegistrationRequestDto("John", "Doe", "johndoe")

        transaction = Transaction(amount = BigDecimal("50.00"), type = TransactionType.IN, player = player)
        transactionDto = TransactionDto(amount = BigDecimal("50.00"), type = TransactionType.IN)
    }

    @Test
    fun register_should_create_new_player() {
        // given
        whenever(playerService.createPlayer("John", "Doe", "johndoe")).thenReturn(player)

        // when
        val response: ResponseEntity<RegistrationResponseDto> = playerController.register(registrationRequestDto)

        // then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(playerUuid, response.body?.playerUuid)
    }

    @Test
    fun get_wallet_details_should_return_balance_and_transactions() {
        // given
        whenever(playerService.getPlayerWalletBalance(playerUuid)).thenReturn(BigDecimal("1000.00"))
        whenever(transactionService.getTransactions(playerUuid)).thenReturn(listOf(transaction))

        // when
        val response: ResponseEntity<GetWalletDetailsResponseDto> = playerController.getWalletDetails(playerUuid)

        // then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(BigDecimal("1000.00"), response.body?.walletBalance)
        assertEquals(1, response.body?.transactions?.size)
    }

    @Test
    fun get_leaderboard_should_return_top_players() {
        // given
        whenever(playerService.getWinningsLeaderboard()).thenReturn(listOf(player))

        // when
        val response: ResponseEntity<GetLeaderboardResponseDto> = playerController.getLeaderboard()

        // then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(1, response.body?.leaderboard?.size)
    }
}
