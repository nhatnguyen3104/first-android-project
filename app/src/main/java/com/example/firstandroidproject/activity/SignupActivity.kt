package com.example.firstandroidproject.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firstandroidproject.R
import com.example.firstandroidproject.classSuport.EmployeeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnSignUp=findViewById<Button>(R.id.btnSignUp)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        //sử lý sự kiện khi click vào Sign up
        btnSignUp.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {

        val edt_firstName = findViewById<EditText>(R.id.edt_firstName)
        val edt_lastName = findViewById<EditText>(R.id.edt_lastName)
        val edt_mail = findViewById<EditText>(R.id.edt_mail)
        val edt_password = findViewById<EditText>(R.id.edt_password)

        //getting value
        val empFirstName = edt_firstName.text.toString()
        val empLastName = edt_lastName.text.toString()
        val empMail = edt_mail.text.toString()
        val empPassword = edt_password.text.toString()

        //push data
        val empId = dbRef.push().key!!
        val User = EmployeeModel(empId, empFirstName, empLastName, empMail, empPassword)

        //check if data is empty
        if (empFirstName.isEmpty()) {
            edt_firstName.error = "Please enter First Name"
            return
        }
        if (empLastName.isEmpty()) {
            edt_lastName.error = "Please enter Last Name"
            return
        }
        if (empMail.isEmpty()) {
            edt_mail.error = "Please enter Email"
            return
        }
        if (empPassword.isEmpty()) {
            edt_password.error = "please enter Password"
            return
        }
        dbRef.child(empId).setValue(User)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                edt_firstName.text.clear()
                edt_lastName.text.clear()
                edt_mail.text.clear()
                edt_password.text.clear()
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}