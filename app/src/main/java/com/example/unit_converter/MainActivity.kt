package com.example.unit_converter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import java.util.*

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
    private val unit2EditText : EditText by lazy{
        findViewById<EditText>(R.id.unit2EditText)
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
    private fun defaultSetting(spinner1Index:Int, editText1Value:String, spinner2Index:Int, editText2Value:String){
        unit1Spinner.setSelection(spinner1Index)
        unit1EditText.setText(editText1Value)
        unit2Spinner.setSelection(spinner2Index)
        unit2EditText.setText(editText2Value)
    }
}