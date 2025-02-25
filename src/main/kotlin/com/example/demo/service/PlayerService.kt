package com.example.demo.service

import com.example.demo.common.model.Player
import org.springframework.stereotype.Service

@Service
class PlayerService {

    fun createUser(): Player {




        val newPlayer = Player()

        return newPlayer
    }

}