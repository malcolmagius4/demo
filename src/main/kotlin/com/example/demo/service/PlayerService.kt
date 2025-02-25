package com.example.demo.service

import com.example.demo.common.model.Player
import com.example.demo.infra.repository.PlayerRepository
import org.springframework.stereotype.Service

@Service
class PlayerService(val playerRepository: PlayerRepository) {

    fun createPlayer(name: String, surname: String, username: String): Player {

        val newPlayer = Player(name = name, surname = surname, username = username)

        return playerRepository.save(newPlayer)
    }

}