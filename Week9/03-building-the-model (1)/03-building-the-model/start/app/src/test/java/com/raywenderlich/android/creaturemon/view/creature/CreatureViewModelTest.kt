package com.raywenderlich.android.creaturemon.view.creature

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureAttributes
import com.raywenderlich.android.creaturemon.model.CreatureGenerator
import com.raywenderlich.android.creaturemon.model.CreatureRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CreatureViewModelTest {
    private lateinit var creatureViewModel: CreatureViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockGenerator: CreatureGenerator

    @Mock
    private lateinit var creatureRepository: CreatureRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        creatureViewModel = CreatureViewModel(mockGenerator, creatureRepository)
    }

    @Test
    fun testSetupCreature() {
        val attributes = CreatureAttributes(10, 3, 7)
        val stubCreature = Creature(attributes, 87, "Test Creature")
        `when`(mockGenerator.generateCreature(attributes)).thenReturn(stubCreature)

        creatureViewModel.intelligence = 10
        creatureViewModel.endurance = 7
        creatureViewModel.strength = 3

        //Update the creature on ViewModel
        creatureViewModel.updateCreature()

        assertEquals(stubCreature, creatureViewModel.creature)
    }

    @Test
    fun testCantSaveCreatureWithBlankNames() {
        creatureViewModel.intelligence = 10
        creatureViewModel.endurance = 7
        creatureViewModel.strength = 3
        creatureViewModel.drawable = 1
        creatureViewModel.name = ""

        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }

    @Test
    fun testCantSaveCreatureWithoutStrength() {
        creatureViewModel.intelligence = 10
        creatureViewModel.endurance = 7
        creatureViewModel.strength = 0
        creatureViewModel.drawable = 1
        creatureViewModel.name = "My Test Creature"

        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }
}