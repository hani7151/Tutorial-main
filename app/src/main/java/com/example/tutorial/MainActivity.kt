package com.example.tutorial

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat



private const val INITIAL_TIP_PRECENT=15
 private const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var etBsaeAmount:EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tvTipAmount:TextView
    private lateinit var tvTotalAmount:TextView
    private lateinit var tvTipPrecent:TextView
    private lateinit var tvTipDiscriptiont:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBsaeAmount=findViewById(R.id.etBsaeAmount)
        seekBar=findViewById(R.id.seekBarTip)
        tvTotalAmount=findViewById(R.id.tvTotalAmount)
        tvTipAmount=findViewById(R.id.tvTipAmount)
        tvTipPrecent=findViewById(R.id.tvTipPrecent)
        tvTipDiscriptiont=findViewById(R.id.tvTipDiscriptiont)

        seekBar.progress= INITIAL_TIP_PRECENT
        tvTipPrecent.text="$INITIAL_TIP_PRECENT%"
        updateTipDiscription(INITIAL_TIP_PRECENT)
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged$progress")
                tvTipPrecent.text="$progress%"
                computeTipAndTotal()
                updateTipDiscription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        etBsaeAmount.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
              Log.i(TAG,"afterTextWatcher $p0")
                computeTipAndTotal()
            }

        })




    }

    private fun updateTipDiscription(tipPercent: Int) {
        val tipDiscription=when(tipPercent){
            in 0..9->"Poor"
            in 10..14->"Acceptable"
            in 15..19->"Good"
            in 20..24->"Great"
            else->"Amazing"



        }

       tvTipDiscriptiont.text=tipDiscription
        val color=ArgbEvaluator().evaluate(
            tipPercent.toFloat()/seekBar.max,
            ContextCompat.getColor(this,R.color.color_worst_tip),
            ContextCompat.getColor(this,R.color.color_best_tip)

        )as Int
           tvTipDiscriptiont.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(etBsaeAmount.text.isEmpty()){
            tvTipAmount.text= ""
            tvTotalAmount.text=""

            return
        }
        val baseAmount=etBsaeAmount.text.toString().toDouble()
        val tipPercent=seekBar.progress
        val tipAmount=baseAmount*tipPercent/100
        val totalAmount= baseAmount+tipAmount
        tvTipAmount.text="%.2f".format(tipAmount)
        tvTotalAmount.text="%.2f".format(totalAmount)

    }
}
