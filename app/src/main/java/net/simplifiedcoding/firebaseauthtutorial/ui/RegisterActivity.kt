package net.simplifiedcoding.firebaseauthtutorial.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import net.simplifiedcoding.firebaseauthtutorial.R
import net.simplifiedcoding.firebaseauthtutorial.utils.login
import net.simplifiedcoding.firebaseauthtutorial.utils.toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        button_register.setOnClickListener {
            val email = text_email.text.toString().trim()
            val password = edit_text_password.text.toString().trim()
            val nickname = text_nickname.text.toString()

            if(nickname.isEmpty()){
                text_nickname.error = "Nickname required"
                text_nickname.requestFocus()
                return@setOnClickListener
            }


            if (email.isEmpty()) {
                text_email.error = "Email Required"
                text_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                text_email.error = "Valid Email Required"
                text_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                edit_text_password.error = "6 char password required"
                edit_text_password.requestFocus()
                return@setOnClickListener
            }

            registerUser(nickname, email, password)

        }

        text_view_login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun registerUser(nickname : String, email: String, password: String) {
        progressbar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressbar.visibility = View.GONE
                if (task.isSuccessful) {
                    inputInitailInformation(nickname, email, mAuth.uid!!)
                    login()
                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }
    }

    private fun inputInitailInformation(nickname: String, email : String, uid : String){
        val user = hashMapOf(
            "userEmail" to email,
            "nickname" to nickname
        )

        db.collection("users").document(uid).set(user)

    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }



    companion object{
        private const val TAG = "Register Test"
    }
}
