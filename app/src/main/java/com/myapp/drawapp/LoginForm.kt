package com.myapp.drawapp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class LoginForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)
        //creating some directories
        createDir()

        val btnlogin =findViewById<Button>(R.id.btn_login)
        val btnsettings =findViewById<Button>(R.id.btn_settings)

        //val value =et_username.text.toString()

        btnlogin.setOnClickListener {
            val et_username=findViewById<EditText>(R.id.et_username_login).text.toString()
            val et_pass=findViewById<EditText>(R.id.et_password_login).text.toString()

            val intent = Intent(this, Field_report::class.java)
            intent.putExtra("name", et_username)
            //intent.putExtra("try",et_pass)
            startActivity(intent)


        }
        btnsettings.setOnClickListener {
//            val et_username=findViewById<EditText>(R.id.et_username_login).text.toString()
//            val et_pass=findViewById<EditText>(R.id.et_password_login).text.toString()
//            Log.d("login13","tru")
//            Toast.makeText(this,et_pass,Toast.LENGTH_LONG).show()
            val tempjson = PdfCreate()
            tempjson.createJson()

        }


    }

    private fun createDir(){
        val rpath = Environment.getExternalStorageDirectory()
        val rDir = File(rpath,"DrawUp")
        rDir.mkdir()
        val dirworks=File(rDir,"Works")
        dirworks.mkdir()
        val dirmachine=File(rDir,"Machines")
        dirmachine.mkdir()

    }
}