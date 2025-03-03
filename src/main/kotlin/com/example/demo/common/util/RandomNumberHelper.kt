package com.example.demo.common.util

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class RandomNumberHelper {

    fun randomNumber(from: Int, until: Int): Int {
        return Random.nextInt(from, until)
    }
}