package com.example.demo.contoller

import com.example.demo.common.dto.*
import com.example.demo.common.model.Player
import com.example.demo.common.model.Transaction
import com.example.demo.service.PlayerService
import com.example.demo.service.TransactionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/v1/player")
class PlayerController(
    val playerService: PlayerService,
    val transactionService: TransactionService
) {

    @PostMapping(value = ["/register"], produces = ["application/json"])
    fun register(@RequestBody @Valid requestDto: RegistrationRequestDto): ResponseEntity<RegistrationResponseDto> {

        val newPlayer: Player =
            playerService.createPlayer(name = requestDto.name, surname = requestDto.surname, username = requestDto.username)

        return ResponseEntity(RegistrationResponseDto(playerUuid = newPlayer.playerUuid, name = newPlayer.name,
            surname = newPlayer.surname, username = newPlayer.username, walletBalance = newPlayer.walletBalance),
            HttpStatus.CREATED)
    }

    @GetMapping(value = ["/{playerUuid}/wallet-details"], produces = ["application/json"])
    fun getWalletDetails(@PathVariable(value="playerUuid") playerUuid: UUID): ResponseEntity<GetWalletDetailsResponseDto> {

        val walletBalance: BigDecimal = playerService.getPlayerWalletBalance(playerUuid)

        val transactions: List<TransactionDto> =
            transactionService.getTransactions(playerUuid).map { t: Transaction ->  t.toDto()}

        return ResponseEntity.ok(GetWalletDetailsResponseDto(walletBalance, transactions))
    }

    @GetMapping(value = ["/winnings/leaderboard"], produces = ["application/json"])
    fun getLeaderboard(): ResponseEntity<GetLeaderboardResponseDto> {

        val leaderboard = playerService.getWinningsLeaderboard().map { p -> p.toLeaderboardDto()}

        return ResponseEntity.ok(GetLeaderboardResponseDto(leaderboard))
    }

}