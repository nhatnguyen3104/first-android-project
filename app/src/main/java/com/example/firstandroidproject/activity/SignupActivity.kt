package com.example.firstandroidproject.activity

import android.content.Intent
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
import com.example.firstandroidproject.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbRef : DatabaseReference
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnSignUp=findViewById<Button>(R.id.btnSignUp)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnBack.setOnClickListener {
            finish()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        //sử lý sự kiện khi click vào Sign up
        binding.btnSignUp.setOnClickListener {
            saveUserData()
        }

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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

        if(empMail.isNotEmpty()&&empPassword.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(empMail, empPassword).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
        }
    }
}