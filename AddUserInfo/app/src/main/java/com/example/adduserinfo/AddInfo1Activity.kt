package com.example.adduserinfo

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.adduserinfo.databinding.ActivityAddInfo1Binding


class AddInfo1Activity : AppCompatActivity() {
    val binding by lazy { ActivityAddInfo1Binding.inflate(layoutInflater) }

    fun checkEngName(lastName: String, firstName: String): Boolean {
        if (lastName != "" && firstName != "") {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        //받아온 이름, 생년월일을 display
        binding.name.text = intent.getStringExtra("name")
        val birthData = intent.getStringExtra("birth")?.split('.')
        binding.bitrhYear.text = birthData?.get(0)
        binding.birthMonth.text = birthData?.get(1)
        binding.birthDay.text = birthData?.get(2)
        // 서버쪽으로 넘겨야할 값들
        var engLastName: String
        var engFirstName: String
        var gender: Int = 0
        var nation: String = ""
        var domestic: String = ""
        // 우편번호
        var zipCode: String
        var addressText: String
        var addressDetail: String = ""

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = Intent(this, AddInfo2Activity::class.java)


        //영문 이름

        val nationData =
            listOf("-국적을 선택하세요-", "대한민국(KR)", "미국(US)", "일본(JP)", "중국(CH)", "영국(UK)", "필리핀(PH)")
        val nationCode = listOf("", "KR", "US", "JP", "CH", "UK", "PH")
        var nationAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, nationData)

        binding.nationSpinner.adapter = nationAdapter

        binding.nationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 > 0) {
                    //nation변수에 국가 코드 전달
                    nation = nationCode[p2]
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        //성별 radio group
        binding.genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.maleBtn.id -> gender = 1
                binding.femaleBtn.id -> gender = 2
            }
        }
        // 국내 거주 여부 radio group
        binding.domesticRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.domesticBtn.id -> domestic = "DM"
                binding.foreignBtn.id -> domestic = "FR"
            }
        }


        //다음 페이지로 넘어가기 전 확인
        /*binding.nextBtn.setOnClickListener {
            engLastName = binding.engLastName.text.toString()
            engFirstName = binding.engFirstName.text.toString()
            addressDetail = binding.addrDetail.text.toString()
            Log.d(
                "result",
                "engLast: ${engLastName}, engFirst: ${engFirstName},  gender: ${gender}, nation: ${nation}, addressDetail: ${addressDetail} "
            )

            when {
                checkEngName(engLastName, engFirstName) == false -> {
                    Log.d("not", "input eng name ")
                    binding.engNameWarning.setText("영문 이름을 입력하세요!")
                }
                gender == 0 -> {
                    binding.engNameWarning.setText("")
                    Log.d("not", "Choose gender ")
                    binding.genderWarning.setText("성별을 선택하세요")
                }
                nation == "" -> {
                    binding.genderWarning.setText("")
                    Log.d("not", "Choose Nation ")
                    binding.nationWarning.setText("국가를 선택하세요")
                }
                domestic == "" -> {
                    binding.nationWarning.setText("")
                    Log.d("not", "Choose Domestic ")
                    binding.domesticWarning.setText("거주여부를 선택하세요")
                }
                addressDetail == "" -> {
                    binding.domesticWarning.setText("")
                    Log.d("not", "Input detail Address")
                    binding.detailAddressWarning.setText("상세주소를 입력하세요")
                }
                else -> startActivity(intent)

            }
        }*/

        binding.let { it2 ->
            it2.nextBtn.setOnClickListener {
                engLastName = it2.engLastName.text.toString()
                engFirstName = it2.engFirstName.text.toString()
                addressDetail = it2.addrDetail.text.toString()
                Log.d("result", "engLast: ${engLastName}, engFirst: ${engFirstName},  gender: ${gender}, nation: ${nation}, addressDetail: ${addressDetail} ")

                when {
                    !checkEngName(engLastName, engFirstName) -> {
                        Log.d("not", "input eng name ")
                        binding.engNameWarning.text = "영문 이름을 입력하세요!"
                    }
                    gender == 0 -> {
                        binding.engNameWarning.setText("")
                        Log.d("not", "Choose gender ")
                        binding.genderWarning.setText("성별을 선택하세요")
                    }
                    nation == "" -> {
                        binding.genderWarning.setText("")
                        Log.d("not", "Choose Nation ")
                        binding.nationWarning.setText("국가를 선택하세요")
                    }
                    domestic == "" -> {
                        binding.nationWarning.setText("")
                        Log.d("not", "Choose Domestic ")
                        binding.domesticWarning.setText("거주여부를 선택하세요")
                    }
                    addressDetail == "" -> {
                        binding.domesticWarning.setText("")
                        Log.d("not", "Input detail Address")
                        binding.detailAddressWarning.setText("상세주소를 입력하세요")
                    }
                    else -> startActivity(intent)

                }
            }
        }
        //개발용 임시로 "주소검색"누르면 다음 페이지 이동
        binding.addrSearch.setOnClickListener { startActivity(intent) }


    }

}
