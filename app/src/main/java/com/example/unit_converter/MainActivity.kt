package com.example.unit_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isDigitsOnly
import android.text.TextWatcher
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
    private val unit2EditText : TextView by lazy{
        findViewById<TextView>(R.id.unitTextView)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //라디오 버튼
        categoryRadioGroup.setOnCheckedChangeListener{ group, checkId ->
            //카테고리 내용 연결 (라디오버튼 -> 스피너 연결)
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
                // 입력 예외 처리
                val text : String = unit1EditText.getText().toString()
                if (!isDigitsOnly(p0)){ //숫자가 아닌 문자가 포함될 경우
                    unit1EditText.setText("")
                    Toast.makeText(this@MainActivity, "숫자만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
                    return
                }
                if(text.length > 1 && text.substring1(0,1)=="0"){
                    // 숫자 0은 가능하지만 01, 023등은 불가능함
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
            }
        })
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
        unit2EditText.setText(TextViewValue)
    }
}

private fun EditText.onTextChanged(function: () -> Unit) {

}
