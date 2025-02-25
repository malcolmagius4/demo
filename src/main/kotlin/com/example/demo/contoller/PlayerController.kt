package com.example.demo.contoller

import com.example.demo.common.dto.RegistrationRequestDto
import com.example.demo.common.dto.RegistrationResponseDto
import com.example.demo.common.model.Player
import com.example.demo.service.PlayerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController(value = "/v1/player")
class PlayerController(val playerService: PlayerService) {

    @PostMapping(value = ["/register"], produces = ["application/json"])
    fun register(@Valid requestDto: RegistrationRequestDto): ResponseEntity<RegistrationResponseDto> {

        val newPlayer: Player =
            playerService.createPlayer(name = requestDto.name, surname = requestDto.surname, username = requestDto.username)

        return ResponseEntity(RegistrationResponseDto(playerUuid = newPlayer.playerUuid, name = newPlayer.name,
            surname = newPlayer.surname, username = newPlayer.username, walletBalance = newPlayer.walletBalance),
            HttpStatus.CREATED)
    }

    @GetMapping(value = ["/wallet"], produces = ["application/json"])
    fun getWallet() {

    }



}