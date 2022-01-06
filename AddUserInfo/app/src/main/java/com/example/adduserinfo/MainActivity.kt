package com.example.adduserinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adduserinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = Intent(this, AddInfo1Activity::class.java)
        intent.putExtra("name", "양준식")
        intent.putExtra("birth","1996.04.20")
        //이미 휴대폰, 이메일인증과 함께 이름,생년월일이 끝난 후이므로로
       binding.goBtn.setOnClickListener {startActivity(intent)}
    }
}