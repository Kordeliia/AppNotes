package com.example.appnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnotas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var notesAdapter : NoteAdapter
    private lateinit var notesFinAdapter : NoteAdapter
    private lateinit var database : DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = DataBaseHelper(this)
        notesAdapter = NoteAdapter(mutableListOf(), this)
        binding.rvPendNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesAdapter
        }
        notesFinAdapter = NoteAdapter(mutableListOf(), this)
        binding.rvFinNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesFinAdapter
        }
        binding.btnAdd.setOnClickListener {
            if(binding.etAddNote.text.toString().isNotBlank()){
                val note = Note((notesAdapter.itemCount + 1).toLong(),
                    binding.etAddNote.text.toString().trim())
                addNoteAuto(note)
                binding.etAddNote.text?.clear()
            }
            else{
                binding.etAddNote.error = getString(R.string.etAdd_error_campo_requerido)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getData()
    }
    private fun getData(){
        val data = mutableListOf(
            Note(1, "Tarea 1"),
            Note(2, "Tarea 2"))
        data.forEach{note ->
            addNoteAuto(note)
        }
    }

    override fun onChecked(note: Note) {
        deleteNoteAuto(note)
        addNoteAuto(note)
    }

    override fun onLongClick(note: Note, currentAdapter : NoteAdapter) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.Alert_dialog_delete))
            .setPositiveButton(getString(R.string.btn_ad_eliminar)) { dialogInterface, i ->
                currentAdapter.remove(note)
            }
            .setNegativeButton(getString(R.string.btn_ad_cancelar), null)
        builder.create().show()
    }
    private fun addNoteAuto(note: Note) {
        if(note.isFinished){
            notesFinAdapter.add(note)
        }else{
            notesAdapter.add(note)
        }
    }
    private fun deleteNoteAuto(note: Note) {
        if(note.isFinished){
            notesAdapter.remove(note)
        }else{
            notesFinAdapter.remove(note)
        }
    }

}


