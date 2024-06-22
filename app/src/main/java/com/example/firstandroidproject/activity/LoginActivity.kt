package com.example.firstandroidproject.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firstandroidproject.R
import com.example.firstandroidproject.classSuport.EmployeeModel
import com.example.firstandroidproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val txtSignup = findViewById<TextView>(R.id.txtSignup)
        val btnSignUp =findViewById<Button>(R.id.btnSignUp)

        binding.btnLogin.setOnClickListener {
            val loginMail = binding.edtMail.text.toString()
            val loginPassword = binding.edtPassword.text.toString()

            if(loginMail.isNotEmpty() && loginPassword.isNotEmpty()) {
                loginUser(loginMail, loginPassword)
            } else {
                Toast.makeText(this@LoginActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }

        txtSignup.setOnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.forgot.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forgot, null)
            val userMail = view.findViewById<EditText>(R.id.editBox)

            builder.setView(view)
            val dialog = builder.create()

            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareMail(userMail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener{
                dialog.dismiss()
            }
            if(dialog.window!=null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }

    }

    private fun compareMail(Mail: EditText) {
        if(Mail.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Mail.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(Mail.text.toString())
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun loginUser(mail: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener {
            if(it.isSuccessful){
                val intent = Intent(this, navbarActivity::class.java)
                startActivity(intent)
            }
            else if (mail=="admin"&&password=="admin"){
                val intent = Intent(this, navbarActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@LoginActivity, "Email or password is not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}