package com.swai.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var edNoteName: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button
    lateinit var viewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        edNoteName = findViewById(R.id.edNoteName)
        noteEdt = findViewById(R.id.edNoteDescription)
        saveBtn = findViewById(R.id.idBtnEdit)

        val notType = intent.getStringExtra("notType")
        if (notType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val notDescription = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId", -1)
            saveBtn.text = "Update Note"
            edNoteName.setText(noteTitle)
            noteEdt.setText(notDescription)
        } else {
            saveBtn.text = "Save Note"
        }

        saveBtn.setOnClickListener {
            val notTitle = edNoteName.text.toString()
            val noteDescription = noteEdt.text.toString()
            if (notType.equals("Edit")) {
                if (notTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(notTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteId
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                } else {
                    if (notTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        viewModel.addNote(Note(notTitle, noteDescription, currentDateAndTime))
                        Toast.makeText(this, "$notTitle Added", Toast.LENGTH_LONG).show()
                    }
                }
                startActivity(
                    Intent(applicationContext, MainActivity::class.java)
                )
                this.finish()
            }
        }
    }
}