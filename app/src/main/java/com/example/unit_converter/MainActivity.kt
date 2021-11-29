package com.example.unit_converter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isDigitsOnly
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.text.isDigitsOnly
import java.util.*
import kotlin.text.substring as substring1

class MainActivity : AppCompatActivity() {
    private val unit1Spinner: Spinner by lazy{
        findViewById<Spinner>(R.id.unit1Spinner)
    }
    private val unit2Spinner: Spinner by lazy{
        findViewById<Spinner>(R.id.unit2Spinner)
    }
    private val categoryRadioGroup : RadioGroup by lazy{
        findViewById<RadioGroup>(R.id.categoryRadioGroup)
    }
    private val unit1EditText : EditText by lazy{
        findViewById<EditText>(R.id.unit1EditText)
    }
    private val unitTextView : TextView by lazy{
        findViewById<TextView>(R.id.unitTextView)
    }
    //라디오 버튼 체크 값 저장 변수
    private var checkedId : Int = R.id.lengthRadioBtn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //라디오 버튼 & 스피너 디폴트값 설정하기
        categoryRadioGroup.check(R.id.lengthRadioBtn)
        spinnerConnect(R.array.length_values)
        defaultSetting(2,"1",1,"100")

        //카테고리 내용 연결 (라디오버튼 -> 스피너 연결)
        categoryRadioGroup.setOnCheckedChangeListener{ group, checkId ->
            checkedId = checkId
            when(checkId){
                R.id.lengthRadioBtn->{
                    spinnerConnect(R.array.length_values)
                    defaultSetting(2,"1",1,"100")
                }
                R.id.volumeRadioBtn->{
                    spinnerConnect(R.array.volume_values)
                    defaultSetting(1,"1",0,"1000")
                }
                R.id.timeRadioBtn->{
                    spinnerConnect(R.array.time_values)
                    defaultSetting(3, "1", 2,"60")
                }
                R.id.temperatureRadioBtn->{
                    spinnerConnect(R.array.temperature_values)
                    defaultSetting(0,"0",1,"32")
                }
                R.id.weightRadioBtn->{
                    spinnerConnect(R.array.weight_values)
                    defaultSetting(2,"1",1,"1000")
                }
            }
        }

        //EditText 값 변경 이벤트 처리
        unit1EditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if( unit1EditText.getText().toString().equals("")||unit1EditText.getText().toString() == null){
                    unitTextView.setText("")
                }
                else{
                    val text : String = unit1EditText.getText().toString()
                    if(text.length > 1 && text.substring1(0,1)=="0" && text.substring1(1,2)!="."){
                        // 숫자 0은 가능하지만 01, 023등은 불가능함, 소수점0.a 는 가능
                        unit1EditText.setText(text.subSequence(1, text.length))
                        unit1EditText.setSelection(unit1EditText.length())
                        Toast.makeText(this@MainActivity, "맨 앞 0은 생략합니다.",Toast.LENGTH_SHORT).show()
                        return
                    }else if (text.isNotEmpty() && text.length >10){ //10자리 이상 입력 불가
                        unit1EditText.setText(text.subSequence(0,p1))
                        unit1EditText.setSelection(unit1EditText.length())
                        Toast.makeText(this@MainActivity, "10자리까지 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    converterOnRadioBtn(text)
                }
            }
        })
        // 스피너 변경 이벤트 처리
        unit1Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                converterOnRadioBtn(unit1EditText.getText().toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "단위를 선택해주세요.", Toast.LENGTH_SHORT)
                return
            }
        }
        unit2Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                converterOnRadioBtn(unit1EditText.getText().toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "단위를 선택해주세요.", Toast.LENGTH_SHORT)
                return
            }
        }

    }
    //키보드 내리기 이벤트 메서드
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    private fun spinnerConnect(arrayName:Int){
        ArrayAdapter.createFromResource(
            this,
            arrayName,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            unit1Spinner.adapter = adapter
            unit2Spinner.adapter = adapter
        }
    }
    private fun defaultSetting(spinner1Index:Int, editText1Value:String, spinner2Index:Int, TextViewValue:String){
        unit1Spinner.setSelection(spinner1Index)
        unit1EditText.setText(editText1Value)
        unit2Spinner.setSelection(spinner2Index)
        unitTextView.setText(TextViewValue)
    }
    private fun converterOnRadioBtn(text:String){
        when(checkedId){
            R.id.lengthRadioBtn->{
                lengthConverter(unit1Spinner.selectedItem.toString(), unit2Spinner.selectedItem.toString(), text)
            }
            R.id.volumeRadioBtn->{
                volumeConverter(unit1Spinner.selectedItem.toString(), unit2Spinner.selectedItem.toString(), text)
            }
            R.id.timeRadioBtn->{
                timeConverter(unit1Spinner.selectedItem.toString(), unit2Spinner.selectedItem.toString(), text)
            }
            R.id.temperatureRadioBtn->{
                temperatureConverter(unit1Spinner.selectedItem.toString(), unit2Spinner.selectedItem.toString(), text)
            }
            R.id.weightRadioBtn->{
                weightConverter(unit1Spinner.selectedItem.toString(), unit2Spinner.selectedItem.toString(), text)
            }
        }
    }
    private fun lengthConverter(unit1 : String, unit2 : String, value : String){
        when(unit1){
            "mm" -> {
                when (unit2) {
                    "mm" -> unitTextView.setText(value)
                    "cm" -> unitTextView.setText("${value.toDouble() * 0.1}")
                    "m" -> unitTextView.setText("${value.toDouble() * 0.001}")
                    "km" -> unitTextView.setText("${value.toDouble() * 1e-6}")
                    "inch" -> unitTextView.setText("${value.toDouble() * 0.0393701}")
                }
            }
            "cm"->{
                when (unit2) {
                    "mm" -> unitTextView.setText("${value.toDouble() * 10}")
                    "cm" -> unitTextView.setText(value)
                    "m" -> unitTextView.setText("${value.toDouble() * 0.01}")
                    "km" -> unitTextView.setText("${value.toDouble() * 1e-5}")
                    "inch" -> unitTextView.setText("${value.toDouble() * 0.393701}")
                }
            }
            "m"->{
                when (unit2) {
                    "mm" -> unitTextView.setText("${value.toDouble() * 1000}")
                    "cm" -> unitTextView.setText("${value.toDouble() * 100}")
                    "m" -> unitTextView.setText(value)
                    "km" -> unitTextView.setText("${value.toDouble() * 0.001}")
                    "inch" -> unitTextView.setText("${value.toDouble() * 0.393701}")
                }
            }
            "km"->{
                when (unit2) {
                    "mm" -> unitTextView.setText("${value.toDouble() * 1e+6}")
                    "cm" -> unitTextView.setText("${value.toDouble() * 100000}")
                    "m" -> unitTextView.setText("${value.toDouble() * 1000}")
                    "km" -> unitTextView.setText(value)
                    "inch" -> unitTextView.setText("${value.toDouble() * 39370.1}")
                }
            }
            "inch" -> {
                when (unit2) {
                    "mm" -> unitTextView.setText("${value.toDouble() * 25.4}")
                    "cm" -> unitTextView.setText("${value.toDouble() * 2.54}")
                    "m" -> unitTextView.setText("${value.toDouble() * 0.0254}")
                    "km" -> unitTextView.setText("${value.toDouble() * 2.54e-5}")
                    "inch" -> unitTextView.setText(value)
                }
            }
        }
    }
    private fun volumeConverter(unit1 : String, unit2 : String, value : String){
        when(unit1){
            "mL" -> {
                when(unit2){
                    "mL" -> unitTextView.setText(value)
                    "L" -> unitTextView.setText("${value.toDouble()*0.001}")
                }
            }
            "L"->{
                when(unit2){
                    "mL"->unitTextView.setText("${value.toDouble()*1000}")
                }
            }
        }
    }
    private fun timeConverter(unit1 : String, unit2 : String, value : String){
        when(unit1){
            "밀리초"->{
                when(unit2){
                    "밀리초" -> unitTextView.setText(value)
                    "초" -> unitTextView.setText("${value.toDouble()*0.001}")
                    "분" -> unitTextView.setText("${value.toDouble()*1.6667e-5}")
                    "시" -> unitTextView.setText("${value.toDouble()*2.7778e-7}")
                }
            }
            "초"->{
                when(unit2){
                    "밀리초" -> unitTextView.setText("${value.toDouble()*1000}")
                    "초" -> unitTextView.setText(value)
                    "분" -> unitTextView.setText("${value.toDouble()*0.0166667}")
                    "시" -> unitTextView.setText("${value.toDouble()*2.7778e-4}")
                }
            }
            "분"->{
                when(unit2){
                    "밀리초" -> unitTextView.setText("${value.toDouble()*60000}")
                    "초" -> unitTextView.setText("${value.toDouble()*60}")
                    "분" -> unitTextView.setText(value)
                    "시" -> unitTextView.setText("${value.toDouble()*0.0166667}")
                }
            }
            "시"->{
                when(unit2){
                    "밀리초" -> unitTextView.setText("${value.toDouble()*3.6e+6}")
                    "초" -> unitTextView.setText("${value.toDouble()*3600}")
                    "분" -> unitTextView.setText("${value.toDouble()*60}")
                    "시" -> unitTextView.setText(value)
                }
            }
        }
    }
    private fun temperatureConverter(unit1 : String, unit2 : String, value : String){
        when(unit1){
            "섭씨(℃)"->{
                when(unit2){
                    "섭씨(℃)"->unitTextView.setText(value)
                    "화씨(°F)"->unitTextView.setText("${(value.toDouble()*9/5)+32}")
                }
            }
            "화씨(°F)"->{
                when(unit2){
                    "섭씨(℃)"->unitTextView.setText("${(value.toDouble()-32)*5/9}")
                    "화씨(°F)"->unitTextView.setText(value)
                }
            }
        }
    }
    private fun weightConverter(unit1 : String, unit2 : String, value : String){
        when(unit1){
            "mg"->{
                when(unit2){
                    "mg"->unitTextView.setText(value)
                    "g"->unitTextView.setText("${value.toDouble()*0.001}")
                    "kg"->unitTextView.setText("${value.toDouble()*1e-6}")
                    "t"->unitTextView.setText("${value.toDouble()*1e-9}")
                    "lb"->unitTextView.setText("${value.toDouble()*2.2046e-6}")
                }
            }
            "g"->{
                when(unit2){
                    "mg"->unitTextView.setText("${value.toDouble()*1000}")
                    "g"->unitTextView.setText(value)
                    "kg"->unitTextView.setText("${value.toDouble()*0.001}")
                    "t"->unitTextView.setText("${value.toDouble()*1e-6}")
                    "lb"->unitTextView.setText("${value.toDouble()*2.2046e-3}")
                }
            }
            "kg"->{
                when(unit2){
                    "mg"->unitTextView.setText("${value.toDouble()*1e+6}")
                    "g"->unitTextView.setText("${value.toDouble()*1000}")
                    "kg"->unitTextView.setText(value)
                    "t"->unitTextView.setText("${value.toDouble()*0.001}")
                    "lb"->unitTextView.setText("${value.toDouble()*2.20462}")
                }
            }
            "t"->{
                when(unit2){
                    "mg"->unitTextView.setText("${value.toDouble()*1e+9}")
                    "g"->unitTextView.setText("${value.toDouble()*1e+6}")
                    "kg"->unitTextView.setText("${value.toDouble()*1000}")
                    "t"->unitTextView.setText(value)
                    "lb"->unitTextView.setText("${value.toDouble()*2204.62}")
                }
            }
            "lb"->{
                when(unit2){
                    "mg"->unitTextView.setText("${value.toDouble()*453592}")
                    "g"->unitTextView.setText("${value.toDouble()*453.592}")
                    "kg"->unitTextView.setText("${value.toDouble()*0.453592}")
                    "t"->unitTextView.setText("${value.toDouble()*4.53592e-4}")
                    "lb"->unitTextView.setText(value)
                }
            }
        }
    }
}
