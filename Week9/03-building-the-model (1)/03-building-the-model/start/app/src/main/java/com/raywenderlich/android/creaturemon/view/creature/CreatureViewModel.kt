package com.raywenderlich.android.creaturemon.view.creature

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.model.*

class CreatureViewModel(private val creatureGenerator: CreatureGenerator = CreatureGenerator()) : ViewModel() {

    private val creatureLiveData = MutableLiveData<Creature>()

    fun getCreatureLiveData(): LiveData<Creature> = creatureLiveData
    var name = ""
    var intelligence = 0
    var strength = 0
    var endurance = 0
    var drawable = 0

    lateinit var creature: Creature

    fun updateCreature() {
        val attributes = CreatureAttributes(intelligence, strength, endurance)
        creature = creatureGenerator.generateCreature(attributes, name, drawable)
        creatureLiveData.postValue(creature)

    }

    fun attributesSelected(attributeType: AttributeType, position: Int) {
        when (attributeType) {
            AttributeType.INTELLIGENCE -> intelligence = AttributeStore.INTELLIGENCE[position].value
            AttributeType.ENDURANCE -> endurance = AttributeStore.ENDURANCE[position].value
            AttributeType.STRENGTH -> strength = AttributeStore.STRENGTH[position].value
        }
        updateCreature()
    }

    //    When avatar is selected
    fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        updateCreature()

    }
}