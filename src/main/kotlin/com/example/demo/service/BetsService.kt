package com.example.demo.service

import com.example.demo.common.model.Bet
import com.example.demo.infra.repository.BetRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BetsService(val playerService: PlayerService, val betRepository: BetRepository) {

    fun getBets(playerUuid: UUID): Set<Bet> {
        val player = playerService.getPlayer(playerUuid)

        return betRepository.findByPlayer(player)
    }

}