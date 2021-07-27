package com.makeus.eatoo.src.home.group.category.category_list

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentGroupCategoryListBinding

class GroupCategoryListFragment
    : BaseFragment<FragmentGroupCategoryListBinding>(FragmentGroupCategoryListBinding::bind, R.layout.fragment_group_category_list)
{


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()

    }

    private fun setSpinner() {
        val spinnerItem = resources.getStringArray(R.array.spinner_category)

        context?.let {
            val arrayAdapter = ArrayAdapter(
                it,
                R.layout.mate_status_spinner_item,
                spinnerItem
            )
            binding.spinnerCategory.adapter = arrayAdapter
        }


//        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
////                val selectedItem = binding.spinnerCategory.getItemAtPosition(i)
//                //서버 연결.
//                if(i == 0) {
//                    showCustomToast("0 clicked")
//                }
//                else showCustomToast("1 clicked!")
//            }
//
//            override fun onNothingSelected(adapterView: AdapterView<*>) {
//
//            }
//        }

    }
}