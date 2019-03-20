package com.example.fruitsdiary.usecase.home

import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment

abstract class HomeAbstractFragment : Fragment() {

    abstract fun  getFragmentName(): String

    abstract fun setFabAction(fab: FloatingActionButton)
}
