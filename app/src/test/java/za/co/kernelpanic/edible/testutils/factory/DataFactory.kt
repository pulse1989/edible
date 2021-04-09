package za.co.kernelpanic.edible.testutils.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String = UUID.randomUUID().toString().substring(0, 5)

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)

    fun randomLong(): Long = randomInt().toLong()

    fun randomDouble(): Double = randomInt() / 23.5
}