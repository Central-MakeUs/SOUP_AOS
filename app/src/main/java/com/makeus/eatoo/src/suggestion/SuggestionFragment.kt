package com.makeus.eatoo.src.suggestion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseFragment
import com.makeus.eatoo.config.BaseResponse
import com.makeus.eatoo.databinding.FragmentSuggestionBinding
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.src.suggestion.adpater.MateSuggestionRecyclerviewAdapter
import com.makeus.eatoo.src.suggestion.model.SuggestionMateResponse
import com.makeus.eatoo.util.getUserIdx
import com.makeus.eatoo.util.getUserNickName


class SuggestionFragment
    : BaseFragment<FragmentSuggestionBinding>(FragmentSuggestionBinding::bind, R.layout.fragment_suggestion),
    MySuggestionView , MateSuggestionRecyclerviewAdapter.OnMySuggestClick, MySuggestDeleteDialogInterface{


    private lateinit var mateAdapter : MateSuggestionRecyclerviewAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.userNameSuggestionTxt.text = getUserNickName() + binding.userNameSuggestionTxt.text


        getMySuggestion()


    }
    private fun getMySuggestion() {
        context?.let {
            showLoadingDialog(it)
            MySuggestionService(this).tryGetMateData(getUserIdx())
        }
    }

    override fun mySuggestLongClicked(mateTitle: String, mateIdx: Int) {
        context?.let {
            val dialog = MySuggestDeleteDialog(it, this,  mateIdx, mateTitle)
            dialog.show()
        }

    }

    override fun onMySuggestConfirm(mateIdx: Int) {
        context?.let {
            showLoadingDialog(it)
            MySuggestionService(this).tryDeleteMate(getUserIdx(), mateIdx)
        }
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

                context?.let {
                    mateAdapter = MateSuggestionRecyclerviewAdapter(it, response.result, this)
                    binding.mySuggestionRecyclerview.apply {
                        adapter = mateAdapter
                        layoutManager =
                            LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.VERTICAL }
                    }
                }

            }
        }
        else{
            binding.mySuggestionRecyclerview.visibility = View.GONE
            binding.mySuggestionNoneLayout.visibility = View.VISIBLE
        }
    }

    override fun onGetMateFail(message: String) {
    }

    override fun onDeleteSuggestSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        getMySuggestion()
        mateAdapter.notifyDataSetChanged()
    }

    override fun onDeleteSuggestFail(message: String?) {
        dismissLoadingDialog()
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}