package com.example.demo.service

import com.example.demo.common.enum.ErrorCode
import com.example.demo.common.exception.BusinessRuntimeException
import com.example.demo.common.model.Player
import com.example.demo.infra.repository.PlayerRepository
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
class PlayerServiceTest {

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @InjectMocks
    private lateinit var playerService: PlayerService

    @Test
    fun create_player_should_throw_exception_if_username_is_taken() {
        //given
        val username = "existingUsername"
        val player = Player(id = 1, playerUuid = UUID.randomUUID(), name = "John", surname = "Doe", username = username)

        //when
        whenever(playerRepository.findByUsername(username)).thenReturn(player)

        //then
        val exception = assertThrows<BusinessRuntimeException> {
            playerService.createPlayer("John", "Doe", username)
        }

        assertEquals(ErrorCode.USERNAME_TAKEN, exception.errorCode)
        assertEquals(400, exception.httpStatus)
    }

    @Test
    fun create_player_should_create_player_successfully_if_username_is_not_taken() {
        //given
        val username = "newUsername"
        val player = Player(id = 1, playerUuid = UUID.randomUUID(), name = "John", surname = "Doe", username = username)

        //when
        whenever(playerRepository.findByUsername(username)).thenReturn(null)
        whenever(playerRepository.save(any<Player>())).thenReturn(player)

        //then
        val createdPlayer = playerService.createPlayer("John", "Doe", username)
        assertEquals(username, createdPlayer.username)
        verify(playerRepository).save(any())
    }

    @Test
    fun get_player_wallet_balance_should_return_wallet_balance_when_player_exists() {
        //given
        val playerUuid = UUID.randomUUID()
        val player = Player(id = 1, playerUuid = playerUuid, name = "John", surname = "Doe", username = "john_doe", walletBalance = BigDecimal("100.00"))

        //when
        whenever(playerRepository.findByPlayerUuid(playerUuid)).thenReturn(player)

        //then
        val walletBalance = playerService.getPlayerWalletBalance(playerUuid)
        assertEquals(BigDecimal("100.00"), walletBalance)
    }

    @Test
    fun get_player_wallet_balance_should_throw_exception_when_player_not_found() {
        //given
        val playerUuid = UUID.randomUUID()

        //when
        whenever(playerRepository.findByPlayerUuid(playerUuid)).thenReturn(null)

        //then
        val exception = assertThrows<BusinessRuntimeException> {
            playerService.getPlayerWalletBalance(playerUuid)
        }

        assertEquals(ErrorCode.PLAYER_UUID_NOT_FOUND, exception.errorCode)
        assertEquals(404, exception.httpStatus)
    }

    @Test
    fun player_exists_should_return_true_if_player_exists() {
        //given
        val playerUuid = UUID.randomUUID()

        //when
        whenever(playerRepository.existsPlayerByPlayerUuid(playerUuid)).thenReturn(true)

        //then
        val exists = playerService.playerExists(playerUuid)
        assertTrue(exists)
    }

    @Test
    fun player_exists_should_return_false_if_player_does_not_exist() {
        //given
        val playerUuid = UUID.randomUUID()

        //when
        whenever(playerRepository.existsPlayerByPlayerUuid(playerUuid)).thenReturn(false)

        //then
        val exists = playerService.playerExists(playerUuid)
        assertFalse(exists)
    }

    @Test
    fun get_winnings_leaderboard_should_return_top_10_players_based_on_winnings() {
        //given
        val player1 = Player(id = 1, playerUuid = UUID.randomUUID(), name = "John", surname = "Doe",
            username = "john_doe", walletBalance = BigDecimal("100.00"))
        val player2 = Player(id = 2, playerUuid = UUID.randomUUID(), name = "Jane", surname = "Doe",
            username = "jane_doe", walletBalance = BigDecimal("200.00"))

        val playerList = listOf(player1, player2)

        //when
        whenever(playerRepository.findTop10ByOrderByTotalWinningsDesc()).thenReturn(playerList)

        //then
        val leaderboard = playerService.getWinningsLeaderboard()
        assertEquals(2, leaderboard.size)
        assertTrue(leaderboard.contains(player1))
        assertTrue(leaderboard.contains(player2))
    }
}
