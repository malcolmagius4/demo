package com.example.demo.contoller

import com.example.demo.service.BetsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/v1/bets")
class BetsController(val betsService: BetsService) {

    @PostMapping("/place-bet")
    fun placeBet() {

    }

    @GetMapping()
    fun getBets() {

    }
}