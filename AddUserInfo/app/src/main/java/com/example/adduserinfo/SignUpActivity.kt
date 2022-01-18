package com.example.adduserinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import java.util.regex.Pattern


    import android.os.Bundle
    import android.text.Editable
    import android.text.TextWatcher
    import android.view.View
    import android.widget.*
    import com.concokorea.concotrade.R
    import com.concokorea.concotrade.data.local.Consts
    import com.concokorea.concotrade.databinding.ActivityKycBinding
    import com.concokorea.concotrade.ui.base.BaseBindingActivity
    import com.concokorea.concotrade.utils.UiUtils
    import java.util.regex.Pattern



class SignUpActivity : AppCompatActivity(), View.OnClickListener{

        private var name: String = ""

        //영문명
        private var engName: String = ""
        private var isvalidEngName: Boolean = false

        private var birth: String = ""
        private var gender: String = Consts.Gender.MALE.name
        private var nation: String = ""
        private var domestic: String = Consts.Domestic.DOMESTIC.name

        //사용자가 수정 불가능
        private var homeZipCode: String = ""

        //~시~구 까지 나오는 포괄적인 주소
        private var homeRegion: String = ""

        //세부 주소
        private var detailAddressHome: String = ""
        private var job: String = ""

        //직장명
        private var workplace: String = ""
        private var department: String = ""
        private var rank: String = ""

        //직장 우편번호
        private var workZipCode: String = ""

        //직장 포괄 주소
        private var workRegion: String = ""
        private var detailAddressWork: String = ""

        //직장 전화번호
        private var workPhone: String = ""
        private var isvalidWorkPhone: Boolean = false

        private var moneyFrom: String = ""
        private var deputy: String = "Y"
        private var bank: String = ""
        private var account: String = ""
        private var valiCode: String = ""

        //자영업자용
        private var businessNumber: String = ""
        private var industryClass: String = ""

        private val nationDataList = arrayListOf<String>(
            "국적을 선택해주세요",
            "Republic of Korea(대한민국)",
            "Afghanistan(아프가니스탄)",
            "Albania(알바니아)"
        )
        val jobDataList = arrayListOf(
            "직업을 선택해주세요",
            "자유직/프리랜서",
            "일반 전문직",
            "기타 회사원",
            "공무원",
            "비금융전문직",
            "종교인",
            "군인",
            "전업주부",
            "무직",
            "학생",
            "의료/법조/금융업 근로자",
            "카지노/대부/귀금속/환전업 근로자",
            "가상통화거래업 근로자",
            "개인사업자/자영업자",
            "농림/축수산/광업 근로자",
            "제조/건설/서비스업 근로자"
        )
        val rankDataList = arrayListOf("직위명을 선택해주세요", "사원", "주임", "대리", "과장", "차장", "부장", "임원")
        val moneyfromDataList = arrayListOf("자금출처를 선택해주세요", "근로및연금소득", "퇴직소득", "사업소득")
        val bankDataList =
            arrayListOf("직위명을 선택해주세요", "국민은행", "하나은행", "신한은행", "우리은행", "농협(구 조흥)", "기업은행")
        val industryClassDataList = arrayListOf("업종분류를 선택하세요", "업종1", "업종2", "업종3")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setBinding(R.layout.activity_kyc)

            initData()
            initView()
        }

        private fun initData() {


        }

        private fun initView() {

            getBinding()?.apply {
                clickListener = this@KycActivity


                rgGender.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.btn_male -> {
                            gender = Consts.Gender.MALE.name
                            getBinding()?.tvGenderWarning?.visibility = View.GONE
                        }
                        R.id.btn_female -> {
                            gender = Consts.Gender.FEMALE.name
                            getBinding()?.tvGenderWarning?.visibility = View.GONE
                        }
                    }
                }

                //국내거주여부 RadioGroup
                rgDomestic.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.btn_domestic -> {
                            domestic = Consts.Domestic.DOMESTIC.name
                            getBinding()?.tvDomesticWarning?.visibility = View.GONE
                        }
                        R.id.btn_foreign -> {
                            domestic = Consts.Domestic.FOREITN.name
                            getBinding()?.tvDomesticWarning?.visibility = View.GONE
                        }
                    }
                }

                //대리인 여부 RadioGroup
                rgDeputy.setOnCheckedChangeListener { _, checkedId ->
                    when (checkedId) {
                        R.id.btn_deputy_y -> {
                            deputy = "Y"

                        }
                        R.id.btn_deputy_n -> {
                            deputy = "N"
                            getBinding()?.tvDeputyWarning?.visibility = View.GONE
                        }
                    }
                }

                // 영문명 watcher
                etEngName.addTextChangedListener(object : TextWatcher {

                    override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                        validEngName()
                    }
                    override fun afterTextChanged(s: Editable?) {

                    }
                })

                // 직장 전화번호 check Watcher
                etWorkPhone.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {

                    }
                    override fun onTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
                        validWorkPhone()
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }
                })

            }

            //spinner 설정
            initViewNationSpinnerSetting()
            initViewJobSpinnerSetting()
            initViewRankSpinnerSetting()
            initViewMoneyfromSpinnerSetting()
            initViewBankSpinnerSetting()
            initViewIndustryClassSpinnerSetting()

        }

        private fun initViewNationSpinnerSetting() {

            getBinding()?.apply {
                nationSpinnerList = nationDataList

                nationSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position != 0) {
                            nation = nationDataList[position]
                            tvNationWarning.visibility = View.GONE
                        } else {
                            nation = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // nothing
                    }
                }
            }
        }

        private fun initViewJobSpinnerSetting() {
            getBinding()?.apply {
                jobSpinnerList = jobDataList

                jobSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        when (position) {
                            0 -> {
                                job = ""
                            }
                            in 7..10 -> {
                                job = jobDataList[position]
                                getBinding()?.tvJobWarning?.visibility = View.GONE
                                //직장명,부서,직위,주소,전화 없앰
                                getBinding()?.apply {
                                    //항목 5개 제거
                                    clLineWorkplace.visibility = View.GONE
                                    clLineDepartment.visibility = View.GONE
                                    clLineRank.visibility = View.GONE
                                    clLineWorkAddress.visibility = View.GONE
                                    clLineWorkPhone.visibility = View.GONE
                                    //자영업자 항목 제거
                                    clLineBusinessNumber.visibility = View.GONE
                                    clLineIndustryClass.visibility = View.GONE
                                }
                            }
                            14 -> { // 자영업자 - 부서,직위 없애고, 사업자번호,업종분류 2가지 추가
                                job = jobDataList[position]
                                getBinding()?.tvJobWarning?.visibility = View.GONE
                                getBinding()?.apply {
                                    //직장명,직장주소,직장번호 추가
                                    clLineWorkplace.visibility = View.VISIBLE
                                    clLineWorkAddress.visibility = View.VISIBLE
                                    clLineWorkPhone.visibility = View.VISIBLE
                                    //부서,직위 사라짐
                                    clLineDepartment.visibility = View.GONE
                                    clLineRank.visibility = View.GONE
                                    //자영업자 항목 추가
                                    clLineBusinessNumber.visibility = View.VISIBLE
                                    clLineIndustryClass.visibility = View.VISIBLE
                                }
                            }
                            else -> {
                                job = jobDataList[position]
                                getBinding()?.tvJobWarning?.visibility = View.GONE
                                //직장명,부서,직위,주소,전화 보이게
                                getBinding()?.apply {
                                    //항목 5가지 생성
                                    clLineWorkplace.visibility = View.VISIBLE
                                    clLineDepartment.visibility = View.VISIBLE
                                    clLineRank.visibility = View.VISIBLE
                                    clLineWorkAddress.visibility = View.VISIBLE
                                    clLineWorkPhone.visibility = View.VISIBLE
                                    //자영업자 항목 제거
                                    clLineBusinessNumber.visibility = View.GONE
                                    clLineIndustryClass.visibility = View.GONE
                                }
                            }
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //nothing
                    }
                }
            }
        }

        //직위명 spinner
        private fun initViewRankSpinnerSetting() {
            getBinding()?.apply {
                rankSpinnerList = rankDataList

                rankSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position != 0) {
                            rank = rankDataList[position]
                            getBinding()?.tvRankWarning?.visibility = View.GONE
                        } else {
                            rank = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        }

        //거래자금 출처 spinner
        private fun initViewMoneyfromSpinnerSetting() {
            getBinding()?.apply {
                moneyFromSpinnerList = moneyfromDataList

                moneyFromSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position != 0) {
                            moneyFrom = moneyfromDataList[position]
                            getBinding()?.tvMoneyFromWarning?.visibility = View.GONE
                        } else {
                            moneyFrom = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        }

        //은행 spinner
        private fun initViewBankSpinnerSetting() {
            getBinding()?.apply {
                bankSpinnerList = bankDataList
                bankSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position != 0) {
                            bank = bankDataList[position]
                            getBinding()?.tvBankWarning?.visibility = View.GONE
                        } else {
                            bank = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        }

        //업종분류 spinner
        private fun initViewIndustryClassSpinnerSetting() {
            getBinding()?.apply {
                industryClassSpinnerList = industryClassDataList
                industryClassSpinnerSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (position != 0) {
                            industryClass = industryClassDataList[position]
                            getBinding()?.tvIndustryClassWarning?.visibility = View.GONE
                        } else {
                            industryClass = ""
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        }

        override fun onClick(view: View?) {
            when (view?.id) {

                //?표시 누르면 snackbar 표시 - 영문명
                R.id.iv_question_eng_name, R.id.tv_eng_name_label -> {
                    UiUtils.showSnackBar(view?.rootView, getString(R.string.kyc_question_mark_info_eng_name))
                }

                //?표시 누르면 snackbar 표시 - 직업
                R.id.iv_question_job, R.id.tv_job_label -> {
                    UiUtils.showSnackBar(
                        view?.rootView,
                        getString(R.string.kyc_question_mark_info_job)
                    )
                }

                //?표시 누르면 snackbar 표시 - 대리인 여부
                R.id.iv_question_deputy, R.id.tv_deputy_label -> {
                    UiUtils.showSnackBar(
                        view?.rootView,
                        getString(R.string.kyc_question_mark_info_deputy)
                    )
                }


                //주소검색 버튼
                R.id.btn_search_address -> {
                    //api호출 후 address_num, address_region 초기화
                }

                //직장주소 검색 버튼
                R.id.btn_search_work_address -> {
                    //api호출 후
                }

                //계좌 인증 버튼
                //누르면 계좌번호 창에 입력한 것 가져오고, api연결하여 해당 계좌에 1원 보냄(송금자명에 숫자코드)


                //인증코드 확인 버튼
                R.id.btn_validation_send1 -> {
                    //1원 이체 후 확인 + 6자리 입력되면, 버튼을 (인증)으로 바꿔야함

                }

                //finish button --> check if all items are not empty
                R.id.btn_finish -> {

                    getBinding()?.apply {

                        //상세 주소 입력Edit Text
                        detailAddressHome = etDetailAddress.text.toString().trim()
                        //직장명
                        workplace = etWorkplace.text.toString().trim()
                        //부서명
                        department = etDepartment.text.toString().trim()
                        //직장 상세 주소
                        detailAddressWork = etDetailWorkAddress.text.toString().trim()

                        when {
                            !isvalidEngName -> {
                                tvEngNameWarning.text = getString(R.string.kyc_text_warning_eng_name)
                                tvEngNameWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_eng_name))
                            }
                            gender.isEmpty() -> {
                                tvGenderWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_gender))
                            }
                            nation.isEmpty() -> {
                                tvNationWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_nation) )
                            }
                            domestic.isEmpty() -> {
                                tvDomesticWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_domestic) )
                            }
                            detailAddressHome.isEmpty() -> {
                                tvDetailAddressWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_detail_address))
                            }
                            job.isEmpty() -> {
                                tvJobWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_job))
                            }
                            workplace.isEmpty() -> {
                                tvWorkplaceWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_workplace))
                            }
                            department.isEmpty() -> {
                                tvDepartmentWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_department))
                            }
                            rank.isEmpty() -> {
                                tvRankWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_rank))
                            }
                            !isvalidWorkPhone -> {
                                tvWorkPhoneWarning.text =  getString(R.string.kyc_text_warning_workphone)
                                tvWorkPhoneWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_workphone))
                            }
                            moneyFrom.isEmpty() -> {
                                tvMoneyFromWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_money_from))
                            }
                            deputy.isEmpty() -> {
                                tvDeputyWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_deputy))
                            }
                            bank.isEmpty() -> {
                                tvBankWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_bank))
                            }
                            account.isEmpty() -> {
                                tvAccountWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_account))
                            }
                            valiCode.isEmpty() -> {
                                tvValidationCodeWarning.visibility = View.VISIBLE
                                UiUtils.showSnackBar(root, getString(R.string.kyc_text_warning_validation_code))
                            }
                            //모든 항목 입력 완료 -> 통신하면 됨
                            else -> {
                                //여기서 해주면 할당 한 번만 할 수 있다.
                                engName = etEngName.text.toString().trim()
                                workPhone = etWorkPhone.text.toString().trim()
                            }

                        }
                    }


                }
            }
        }


        private fun validEngName() {
            //임시 logic
            val tempEngName = getBinding()?.etEngName?.text.toString().trim()
            //아무것도 입력하지 않은 경우 ( 썼다가 다 지웠을 때 ), 경고창 사라짐
            if (tempEngName.isEmpty()) {
                getBinding()?.tvEngNameWarning?.visibility = View.GONE
                isvalidEngName = false
                return
            }
            //5자 이상이면 true인 것으로 임시..
            val validationResult = Pattern.matches(Consts.engNameValidation, tempEngName)
            if (validationResult) {
                getBinding()?.tvEngNameWarning?.visibility = View.GONE
                isvalidEngName = true
            } else {
                getBinding()?.tvEngNameWarning?.text = getString(R.string.kyc_validation_warning_eng_name)
                getBinding()?.tvEngNameWarning?.visibility = View.VISIBLE
                isvalidEngName = false

            }
        }


        private fun validWorkPhone() {
            val tempWorkphone = getBinding()?.etWorkPhone?.text.toString().trim()
            //아무것도 입력하지 않은 경우, 경고창 사라짐
            if (tempWorkphone.isEmpty()) {
                getBinding()?.tvWorkPhoneWarning?.visibility = View.GONE
                isvalidWorkPhone = false
                return
            }

            val validationResult = Pattern.matches(Consts.workPhoneValidation, tempWorkphone)
            if (validationResult) {
                //전화번호가 정상인 경우
                //warning창 gone
                isvalidWorkPhone = true
                getBinding()?.tvWorkPhoneWarning?.visibility = View.GONE

            } else {
                //전화번호가 형식에 맞지 않는 경우
                // warning 창 VISIBLE
                isvalidWorkPhone = false
                getBinding()?.tvWorkPhoneWarning?.text = getString(R.string.kyc_validation_warning_work_phone)
                getBinding()?.tvWorkPhoneWarning?.visibility = View.VISIBLE

            }
        }
    }