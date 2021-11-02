package com.example.tetris.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.example.tetris.R
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.authentication.*

class AuthenticationFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.authentication, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    sendEmailButton.setOnClickListener {
     val url = "https://mybot-805bb.firebaseapp.com"
     val actionCodeSettings = ActionCodeSettings.newBuilder()
       .setUrl(url)
       .setHandleCodeInApp(true)
       .setAndroidPackageName("com.example.android", false, null)
       .build()

     val email = editTextTextEmailAddress.text.toString()

     Firebase.auth.sendSignInLinkToEmail(email, actionCodeSettings)
       .addOnCompleteListener { task ->
         if (task.isSuccessful) {
           sendEmailButton.text = "送信済"
           sendEmailButton.isClickable = false
         }else{
           Toast.makeText(context, "メールアドレスを送信出来ませんでした", LENGTH_SHORT).show()
         }
       }
    }
  }
}