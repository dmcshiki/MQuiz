package com.example.mquiz.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mquiz.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth

    private var email =""
    private var password = ""
    private var passwordConfirm = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Cadastro"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Aguarde")
        progressDialog.setMessage("Fazendo o cadastro...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click, begin signup
        binding.registerButton.setOnClickListener {
            //validate data
            validateData()
        }
    }

    private fun validateData() {
        email = binding.emailLbl.text.toString().trim()
        password = binding.passwordLbl.text.toString().trim()
        passwordConfirm = binding.confirmPasswordLbl.text.toString().trim()


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailLbl.error = "E-mail inválido"
        }
        else if (TextUtils.isEmpty(password)){
            binding.passwordLbl.error = "Por favor, digite a senha"
        }
        else if (password.length < 6){
            binding.passwordLbl.error = "A senha deve ter pelo menos 6 caractéres"
        }
        else if (password != passwordConfirm){
            binding.passwordLbl.error = "As senhas são diferentes!"
        }
        else{
            firebaseSignUp()

        }

    }

    private fun firebaseSignUp() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Conta criada com o $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Conta não criada por causa de ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}