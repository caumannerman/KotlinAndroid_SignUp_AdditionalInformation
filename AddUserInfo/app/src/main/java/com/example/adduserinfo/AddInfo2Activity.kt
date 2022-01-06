package com.example.adduserinfo

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.adduserinfo.databinding.ActivityAddInfo2Binding
import java.util.regex.Pattern

const val workPhoneValidation = "^0[0-9]{2}[-]?[0-9]{3,4}[-]?[0-9]{4}$"
class AddInfo2Activity : AppCompatActivity() {
    val binding by lazy { ActivityAddInfo2Binding.inflate(layoutInflater)}

    //직업 선택을 위한 스피너 데이터
    enum class Job(val value:String, val code: String) {
        STUDENT("학생", "std"), OfficeWorker("직장인", "wrk"), Unemployed("무직", "non"), Profession("전문직", "pro"),
        lawyer("변호사", "law"), Teacher("교사", "tcr"), Dayjob("일용직", "one"), SelfEmployed("자영업자", "sep")
    }
    var job: String = ""
    var workplace: String = ""
    //부서명
    var department: String = ""
    //직위명
    var rank: String = ""
    var zipCode: String = ""
    var address: String = ""
    var detailAddress: String = ""
    var workPhone: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, AddInfo3Activity::class.java)

        //직위 spinner
        val rankData = listOf("-직위명을 선택하세요-", "부장", "과장","팀장", "직원", "인턴", "사장", "회장", "대리")
        val rankCode = listOf("","bj","gj","tj","jw","it","sj","hj","dr")
        var rankAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,rankData)
        binding.rankSpinner.adapter = rankAdapter
        binding.rankSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 > 0) {
                    //rank변수에 국가 코드 전달
                    rank = rankCode[p2]
                }else{
                    //선택했다가 다시 "직위를 선택하세요"로 돌아간 경우를 위해
                    rank = ""
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        val jobData = listOf("-직업명을 선택하세요-", "학생", "직장인", "무직", "전문직","변화사","교사","일용직","자영업자")
        val jobCode = listOf("","std","wrk","non","pro","law","tcr","one","sep")
        //직업명 SPINNER
        var adapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1,jobData)
        binding.jobSpinner.adapter = adapter
        binding.jobSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 > 0) {
                    //job변수에 국가 코드 전달
                    job = jobCode[p2]
                }else{
                    job = ""
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        //다음 페이지로 넘어가기 전 확인
        binding.nextBtn.setOnClickListener {
            workplace = binding.workplaceText.text.toString().trim()
            department = binding.departmentText.text.toString().trim()
            detailAddress = binding.detailAddress.text.toString().trim()
            checkPhone()


            Log.d(
                "result",
                "job: ${job}, workplace: ${workplace},  department: ${department}, rank: ${rank}, detailAddress: ${detailAddress}, workPhone: ${workPhone}"
            )

            when {
                job.isEmpty() -> { Log.d("not", "Choose job") }
                workplace.isEmpty() ->  Log.d("not", "Input workplace ")
                department.isEmpty() ->  Log.d("not", "Input department ")
                rank.isEmpty() -> Log.d("not", "Input rank(직위) ")
                detailAddress.isEmpty() -> Log.d("not", "input detail Address ")
                workPhone.isEmpty() ->  Log.d("not", "Input workphone")
                else ->  startActivity(intent)

            }
        }
        //개발용 임시로 "주소검색"누르면 다음 페이지 이동
        binding.searchAddressBtn.setOnClickListener{startActivity(intent)}
    }

    fun checkPhone(): Boolean{
        val workPhoneNum = binding.workPhone.text.toString().trim()
        val p = Pattern.matches(workPhoneValidation, workPhoneNum)
        if(p) {
            workPhone = workPhoneNum
            return true
        }
        return false
    }
}