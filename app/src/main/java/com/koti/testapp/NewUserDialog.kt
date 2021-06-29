package com.koti.testapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class NewUserDialog(context: Context,private val listner: NewUserDialogListner) : Dialog(context) {

    val parentContext:Context by lazy {
        context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_new_person)
        initView()
    }

    fun initView() {
        findViewById<MaterialButton>(R.id.addUser).setOnClickListener {
            validateName()
        }
    }

    fun validateName(){
        val name=findViewById<EditText>(R.id.newName).text.toString().trim()
        if(name.isNotEmpty()){
            listner.addNewPerson(name)
            dismiss()
        }else{
            Toast.makeText(parentContext,"Invalid name",Toast.LENGTH_SHORT).show()
        }
    }

    interface NewUserDialogListner{
        fun addNewPerson(name:String)
        fun userDialogDismissed()
    }
}