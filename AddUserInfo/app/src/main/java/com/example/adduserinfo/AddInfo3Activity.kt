package com.example.adduserinfo

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.adduserinfo.databinding.ActivityAddInfo3Binding

class AddInfo3Activity : AppCompatActivity() {
    val binding by lazy { ActivityAddInfo3Binding.inflate(layoutInflater)}
    var moneyFrom: String = ""
    var dri: String = ""
    var idCard: Boolean = false
    var account: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = Intent(this, FinishActivity::class.java)

        //거래자금 spinner
        val moneyFromData = listOf("-거래자금출처 선택-", "근로및연금소득","증여및 상속","임대보증금","회사지원금,사채","주식채권 매각대금")
        val moneyFromCode = listOf("","c1","c2","c3","c4","c5")
        var moneyFromAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1,moneyFromData)
        binding.moneyFromSpinner.adapter = moneyFromAdapter
        binding.moneyFromSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 > 0) {
                    //변수에 국가 코드 전달
                    moneyFrom = moneyFromCode[p2]
                }else{
                    //선택했다가 다시 " 선택하세요"로 돌아간 경우를 위해
                    moneyFrom = ""
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //대리인여부 radiogroup

        binding.driRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.driYes.id -> dri = "yes"
                binding.driNo.id -> dri = "no"
            }
        }

        //가입완료 버튼
        binding.finishBtn.setOnClickListener {
            when {
                moneyFrom.isEmpty() -> { Log.d("not", "Choose MoneyFrom") }
                dri.isEmpty() -> { Log.d("not", "Choose dri") }
                else ->  startActivity(intent)
            }
        }
    }
}