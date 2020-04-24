package com.example.hyunndymovieapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/*
*  ViewGroup에 확장함수 inflate를 정의
*  attachToRoot는 기본적으로 XML에 루트를 사용하므로 보통 FALSE.
*/
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}