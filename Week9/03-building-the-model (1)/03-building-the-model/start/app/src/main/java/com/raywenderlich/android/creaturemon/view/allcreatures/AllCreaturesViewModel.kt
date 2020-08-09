package com.raywenderlich.android.creaturemon.view.allcreatures

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureRepository
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class AllCreaturesViewModel(val repository: CreatureRepository = RoomRepository()) : ViewModel() {
    //Fetch a list of all creatures from the repository
    fun getAllCreatures(): LiveData<List<Creature>> = repository.getAllCreatures()
    fun clearAllCreatures() = repository.deleteAllCreatures()
}