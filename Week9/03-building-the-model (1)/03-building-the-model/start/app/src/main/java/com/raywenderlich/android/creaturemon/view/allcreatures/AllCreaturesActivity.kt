package com.raywenderlich.android.creaturemon.view.allcreatures

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.raywenderlich.android.creaturemon.R
import com.raywenderlich.android.creaturemon.view.creature.CreatureActivity
import kotlinx.android.synthetic.main.activity_all_creatures.*
import kotlinx.android.synthetic.main.content_all_creatures.*

class AllCreaturesActivity : AppCompatActivity() {

    private val adapter = CreatureAdapter(mutableListOf())
    private val viewModel by lazy { ViewModelProviders.of(this).get(AllCreaturesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_creatures)
        setSupportActionBar(toolbar)

        creaturesRecyclerView.layoutManager = LinearLayoutManager(this)
        creaturesRecyclerView.adapter = adapter

        //Populate the data fetched from the repository to the recyclerview
        viewModel.getAllCreatures().observe(this, Observer { creatures ->
            creatures?.let {
                adapter.updateCreatures(creatures)
            }
        })

        fab.setOnClickListener {
            startActivity(Intent(this, CreatureActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                viewModel.clearAllCreatures()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
