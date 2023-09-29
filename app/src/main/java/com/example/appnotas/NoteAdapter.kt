package com.example.appnotas

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotas.databinding.ItemNoteBinding

class NoteAdapter(var noteList: MutableList<Note>, private val listener : OnClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private lateinit var context : Context
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view)
        fun setListener(note : Note){
            binding.cbFinish.setOnClickListener {
                note.isFinished = (it as CheckBox).isChecked
                listener.onChecked(note)
            }
            binding.root.setOnLongClickListener {
                listener.onLongClick(note, this@NoteAdapter)
                true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int =noteList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList.get(position)
        holder.setListener(note)
        holder.binding.tvDescription.text = note.description
        holder.binding.cbFinish.isChecked = note.isFinished
        if(note.isFinished){
            holder.binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    context.resources.getInteger(R.integer.description_size_finished).toFloat())
        } else {
            holder.binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                context.resources.getInteger(R.integer.description_size_default).toFloat())
        }
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