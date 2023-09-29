package com.example.appnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnotas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var noteAdapter : NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = mutableListOf(
            Note(1, "Tarea 1"),
            Note(2, "Tarea 2"))
        noteAdapter = NoteAdapter(data, this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
        binding.btnAdd.setOnClickListener {
            if(binding.etAddNote.text.toString().isNotBlank()){
                val note = Note((noteAdapter.itemCount + 1).toLong(),
                    binding.etAddNote.text.toString().trim())
                addNoteAuto(note)
            }
        }
    }

    override fun onLongClick(note: Note) {
    }
    private fun addNoteAuto(note: Note) {
        noteAdapter.add(note)
    }
}


