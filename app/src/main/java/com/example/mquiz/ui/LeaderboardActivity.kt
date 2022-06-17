package com.example.mquiz.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.example.mquiz.R
import com.example.mquiz.databinding.ActivityEndGameBinding
import com.example.mquiz.databinding.ActivityLeaderboardBinding
import com.google.firebase.firestore.FirebaseFirestore

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var firebaseStore: FirebaseFirestore
    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Aguarde")
        progressDialog.setMessage("Buscando os melhores jogadores")
        progressDialog.setCanceledOnTouchOutside(false)


        val actionBar = supportActionBar
        actionBar!!.title = "Melhores jogadores"
        actionBar.setDisplayHomeAsUpEnabled(true)

        firebaseStore = FirebaseFirestore.getInstance()
        showBestPlayers()

    }

    private fun showBestPlayers(){
        progressDialog.show()

        firebaseStore.collection("userInfo").get()
            .addOnSuccessListener { it ->

               val data = it.documents.filter {
                    it.data != null
                }.take(5)
                   .map {
                    Result(
                         it.data!!["username"].toString(),
                         it.data!!["score"].toString(),
                    )
                }.sortedByDescending {
                    it.score
                   }


                binding.position1.text = data[0].username
                binding.userRank1.text = data[0].score

                binding.position2.text = data[1].username
                binding.userRank2.text = data[1].score

                binding.position3.text = data[2].username
                binding.userRank3.text = data[2].score

                binding.position4.text = data[3].username
                binding.userRank4.text = data[3].score

                binding.position5.text = data[4].username
                binding.userRank5.text = data[4].score

                progressDialog.dismiss()

            }
    }
}

data class Result(
    val username: String,
    val score: String,
)
