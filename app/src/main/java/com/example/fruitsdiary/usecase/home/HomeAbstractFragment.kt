package com.example.fruitsdiary.usecase.home

import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


abstract class HomeAbstractFragment : Fragment() {

    abstract fun  getFragmentName(): String

    abstract fun setFabAction(fab: FloatingActionButton)
}
