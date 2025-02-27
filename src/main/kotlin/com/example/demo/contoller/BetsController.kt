package com.example.demo.contoller

import com.example.demo.common.dto.BetDto
import com.example.demo.common.dto.GetBetsResponseDto
import com.example.demo.common.model.Bet
import com.example.demo.service.BetsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/bets")
class BetsController(val betsService: BetsService) {

    @PostMapping(value = ["/place-bet"], produces = ["application/json"])
    fun placeBet() {

    }

    @GetMapping(value = ["/{playerUuid}"], produces = ["application/json"])
    fun getBets(@PathVariable(value="playerUuid") playerUuid: UUID) : ResponseEntity<GetBetsResponseDto> {
        val bets: Set<BetDto> = betsService.getBets(playerUuid).map { b: Bet ->  b.toDto()}.toSet()

        return ResponseEntity.ok(GetBetsResponseDto(bets))
    }
}