package com.example.appnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnotas.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

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
                val note = Note(description = binding.etAddNote.text.toString().trim())
                note.id = database.insertNote(note)
                if(note.id != Constants.ID_ERROR){
                    addNoteAuto(note)
                    binding.etAddNote.text?.clear()
                    showMssg(R.string.SB_Operacion_exitosa)
                    Snackbar.make(binding.root,
                        getString(R.string.SB_Operacion_exitosa), Snackbar.LENGTH_SHORT).show()
                } else {
                    showMssg(R.string.SB_Operacion_fallida)
                }
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
       // val data = mutableListOf(
        //    Note(1, "Tarea 1"),
        //    Note(2, "Tarea 2"))
        val data = database.getAllNotes()
        data.forEach{note ->
            addNoteAuto(note)
        }
    }

    override fun onChecked(note: Note) {
        if(database.updateNote(note)){
            deleteNoteAuto(note)
            addNoteAuto(note)
        } else{
            showMssg(R.string.SB_Operacion_fallida)
        }
    }

    override fun onLongClick(note: Note, currentAdapter : NoteAdapter) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.Alert_dialog_delete))
            .setPositiveButton(getString(R.string.btn_ad_eliminar)) { _, _ ->
                if(database.deleteNote(note)){
                    currentAdapter.remove(note)
                    showMssg(R.string.SB_Operacion_exitosa)
                } else{
                    showMssg(R.string.SB_Operacion_fallida)
                }
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
    private fun showMssg(msgRes : Int){
        Snackbar.make(binding.root, getString(msgRes), Snackbar.LENGTH_SHORT).show()
    }
}


