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
    MySuggestionView , MateSuggestionRecyclerviewAdapter.OnMySuggestClick, MySuggestDeleteDialogInterface,
View.OnClickListener{


    private lateinit var mateAdapter : MateSuggestionRecyclerviewAdapter


    override fun onResume() {
        super.onResume()

        getMySuggestion()
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.userNameSuggestionTxt.text = getUserNickName() + binding.userNameSuggestionTxt.text
        binding.clMateSugg.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.cl_mate_sugg -> {
                context?.let {
                    startActivity(Intent(it, MateSuggestionActivity::class.java))
                }

            }
        }
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