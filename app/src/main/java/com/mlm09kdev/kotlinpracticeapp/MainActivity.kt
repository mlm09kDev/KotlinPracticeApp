package com.mlm09kdev.kotlinpracticeapp


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initialTextViewTranslationY = progressTextView.translationY

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progressTextView.text = progress.toString()

                val translationDistance = (initialTextViewTranslationY - progress * resources.getDimension(R.dimen.text_anim_step) * -1)

                progressTextView.animate().translationX(translationDistance)
                if(!fromUser)
                    progressTextView.animate().setDuration(500).rotationBy(360f).translationY(initialTextViewTranslationY)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        resetButton.setOnClickListener{
            seekBar.progress = 0

        }

    }


}


