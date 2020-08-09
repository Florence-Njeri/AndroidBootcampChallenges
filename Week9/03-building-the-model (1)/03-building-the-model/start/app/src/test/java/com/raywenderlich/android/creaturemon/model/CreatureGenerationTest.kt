package com.raywenderlich.android.creaturemon.model

import junit.framework.Assert.assertEquals
import org.junit.Test

class CreatureGenerationTest {
    val creatureGenerator by lazy { CreatureGenerator() }

    @Test
    fun testGeneratorHitPoints() {
        val attributes = CreatureAttributes(intelligence = 7, strength = 3, endurance = 10)
        val name = "Rikachu"

        val expeectedCreature = Creature(attributes = attributes, name = name, hitpoints = 84)

        assertEquals(expeectedCreature, creatureGenerator.generateCreature(attributes,name))
    }
}