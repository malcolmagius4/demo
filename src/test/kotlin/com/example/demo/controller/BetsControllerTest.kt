package com.example.demo.controller

import com.example.demo.common.dto.BetDto
import com.example.demo.common.dto.GetBetsResponseDto
import com.example.demo.common.dto.PlaceBetRequestDto
import com.example.demo.common.model.Bet
import com.example.demo.common.model.Player
import com.example.demo.contoller.BetsController
import com.example.demo.service.BetsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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

@ExtendWith(MockitoExtension::class)
class BetsControllerTest {

    @Mock
    private lateinit var betsService: BetsService

    @InjectMocks
    private lateinit var betsController: BetsController

    private lateinit var playerUuid: UUID
    private lateinit var player: Player
    private lateinit var placeBetRequestDto: PlaceBetRequestDto
    private lateinit var betDto: BetDto
    private lateinit var bet: Bet

    @BeforeEach
    fun setUp() {
        playerUuid = UUID.randomUUID()
        player = Player(playerUuid = playerUuid, name = "John", surname = "Doe", username = "testUsername123", )
        placeBetRequestDto = PlaceBetRequestDto(playerUuid = playerUuid, stakeAmount = BigDecimal("100.00"), betValue = 5)
        betDto = BetDto(BigDecimal("100.00"), BigDecimal("500.00"), 5, 7)
        bet = Bet(player = player, stakeAmount = BigDecimal("100.00"), winAmount = BigDecimal("500.00"), betValue = 5, actualValue = 7)
    }

    @Test
    fun place_bet_should_return_bet_result() {
        // given
        whenever(betsService.placeBet(eq(playerUuid), eq(BigDecimal("100.00")), eq(5))).thenReturn(betDto)

        //when
        val result: ResponseEntity<BetDto> = betsController.placeBet(placeBetRequestDto)

        //then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(betDto, result.body)
    }

    @Test
    fun get_bets_should_return_list_of_bets() {
        //given
        val betDtos = listOf(betDto)

        whenever(betsService.getBets(eq(playerUuid))).thenReturn(listOf(bet))

        //when
        val result: ResponseEntity<GetBetsResponseDto> = betsController.getBets(playerUuid)

        //then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertNotNull(result.body)
        assertEquals(betDtos.size, result.body?.bets?.size)
        assertEquals(betDtos, result.body?.bets)
    }
}
