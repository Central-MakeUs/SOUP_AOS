package com.makeus.eatoo.src.suggestion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.databinding.FragmentSuggestionBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.Group_Mate_Suggetsion_Activity
import com.makeus.eatoo.src.suggestion.adpater.MateSuggestionRecyclerviewAdapter
import com.makeus.eatoo.src.suggestion.model.SuggestionMateResponse
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName


class SuggestionFragment : BaseFragment<FragmentSuggestionBinding>(FragmentSuggestionBinding::bind, R.layout.fragment_suggestion), MySuggestionView {
    var Celebnames = arrayListOf<String>("sdasd","asdas","asdasd")

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.userNameSuggestionTxt.text = getUserNickName() + binding.userNameSuggestionTxt.text


        MySuggestionService(this).tryGetMateData(getUserIdx())
        context?.let { showLoadingDialog(it) }



    }

    override fun onGetMateSuccess(response: SuggestionMateResponse) {
        dismissLoadingDialog()
        if(response.code == 1000) {

            if(response.result.size == 0){
                binding.mySuggestionRecyclerview.visibility = View.GONE
                binding.mySuggestionNoneLayout.visibility = View.VISIBLE
            }
            else{
                binding.mySuggestionRecyclerview.visibility = View.VISIBLE
                binding.mySuggestionNoneLayout.visibility = View.GONE

                val MateList = response.result
                val MateAdapter = MateSuggestionRecyclerviewAdapter(MateList)
                binding.mySuggestionRecyclerview.adapter = MateAdapter
                binding.mySuggestionRecyclerview.layoutManager =
                    LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.VERTICAL }
            }
        }
        else{
            binding.mySuggestionRecyclerview.visibility = View.GONE
            binding.mySuggestionNoneLayout.visibility = View.VISIBLE
        }

    }

    override fun onGetMateFail(message: String) {
    }
}