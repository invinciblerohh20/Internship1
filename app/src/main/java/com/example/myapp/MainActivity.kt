package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val Auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btncreate.setOnClickListener {
            startActivity(Intent(this,SignUPActivity ::class.java))
        }
        btnLogin.setOnClickListener {
            Login()
        }
    }
    private fun Login()
    {
        val username = findViewById(R.id.Name) as EditText
        val pass = findViewById(R.id.Password) as EditText

        var user = username.getText().toString().trim()
        var Pass = pass.getText().toString().trim()


        if(user.isEmpty() && Pass.isEmpty())
            Toast.makeText(this,"Enter Username or password",Toast.LENGTH_SHORT).show()
        else
        {
            Auth.signInWithEmailAndPassword(user,Pass).addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if(task.isSuccessful)
                    {
                        var User: FirebaseUser? = Auth.getCurrentUser()
                        if(User == null)
                        {
                            Toast.makeText(this, "Error occured", Toast.LENGTH_SHORT).show()
                        }
                        else
                            if(User.isEmailVerified) {
                                startActivity(Intent(this, SignInActivity::class.java))
                                finish()
                                Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                Toast.makeText(this, "Please Verify Email", Toast.LENGTH_SHORT).show()
                                User.sendEmailVerification()
                            }
                    }
                    else
                    {
                        Toast.makeText(this,"Sign In Failed",Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
