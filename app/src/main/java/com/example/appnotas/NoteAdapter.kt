package com.example.appnotas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotas.databinding.ItemNoteBinding

class NoteAdapter(var noteList: MutableList<Note>, private val listener : OnClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view)
        fun setListener(note : Note){
            binding.root.setOnLongClickListener {
                listener.onLongClick(note)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int =noteList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList.get(position)
        holder.setListener(note)
        holder.binding.tvDescription.text = note.description
        holder.binding.cbFinish.isChecked = note.isFinished
    }

    fun add(note: Note) {
        noteList.add(note)
        notifyDataSetChanged()
    }
    fun remove(note: Note) {
        noteList.remove(note)
        notifyDataSetChanged()
    }
}