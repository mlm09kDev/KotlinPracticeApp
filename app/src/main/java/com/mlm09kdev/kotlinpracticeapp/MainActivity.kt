package com.mlm09kdev.kotlinpracticeapp


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener{
            change(imageView)
        }

    }

   private fun change( view: View){
       imageView.setImageResource(R.drawable.ic_launcher_foreground)
    }
}


