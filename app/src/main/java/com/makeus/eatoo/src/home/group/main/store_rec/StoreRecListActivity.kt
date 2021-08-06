package com.makeus.eatoo.src.home.group.main.store_rec

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.makeus.eatoo.R
import com.makeus.eatoo.config.BaseActivity
import com.makeus.eatoo.databinding.ActivityStoreRecListBinding
import com.makeus.eatoo.like.LikeService
import com.makeus.eatoo.like.LikeView
import androidx.core.view.isVisible
import com.makeus.eatoo.src.home.group.GroupActivity
import com.makeus.eatoo.src.home.group.category.category_detail.CategoryStoreDetailActivity
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialog
import com.makeus.eatoo.src.home.group.category.dialog.StoreToMateSuggestDialogInterface
import com.makeus.eatoo.src.home.group.groupmatesuggestion.MateSuggestionActivity
import com.makeus.eatoo.src.home.group.main.store_rec.adapter.StoreRecRVAdapter
import com.makeus.eatoo.src.home.group.main.store_rec.model.StoreRecResponse
import com.makeus.eatoo.util.getGroupIdx
import com.makeus.eatoo.util.getGroupName
import com.makeus.eatoo.util.getUserIdx

class StoreRecListActivity :
    BaseActivity<ActivityStoreRecListBinding>(ActivityStoreRecListBinding::inflate),
    RadioGroup.OnCheckedChangeListener, StoreRecRVAdapter.OnStoreClickListener, StoreRecView,
    StoreToMateSuggestDialogInterface, LikeView {

    private var categoryIdx = 1
    private lateinit var storeRecAdapter: StoreRecRVAdapter

    override fun onResume() {
        super.onResume()

        getStoreRecList(binding.spinnerStoreRec.selectedItemPosition)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSpinner()
        binding.rdGroup.setOnCheckedChangeListener(this)
        storeRecAdapter = StoreRecRVAdapter(this, this)
        binding.rvStoreRecList.apply {
            adapter = storeRecAdapter
            layoutManager = GridLayoutManager(this@StoreRecListActivity, 2)
        }


        setBackButton()
        setNoResultGroupNickname()
    }

    @SuppressLint("SetTextI18n")
    private fun setNoResultGroupNickname() {
        binding.tvNoResult1.text = String.format(resources.getString(R.string.no_store_rec), getGroupName())
    }

    private fun setBackButton() {
        binding.customToolbar.leftIcon.setOnClickListener {
            finish()
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when(p1) {
            R.id.rd_btn_ko -> categoryIdx = 1
            R.id.rd_btn_ch -> categoryIdx = 2
            R.id.rd_btn_jp -> categoryIdx = 3
            R.id.rd_btn_western -> categoryIdx = 4
            R.id.rd_btn_street -> categoryIdx = 5
            R.id.rd_btn_asian -> categoryIdx = 6
            R.id.rd_btn_desert -> categoryIdx = 7
            R.id.rd_btn_etc -> categoryIdx = 8
        }
        if(!mLoadingDialog.isShowing) getStoreRecList(binding.spinnerStoreRec.selectedItemPosition)
    }

    private fun setSpinner() {
        val spinnerItem = resources.getStringArray(R.array.spinner_category)

        val arrayAdapter = ArrayAdapter(this, R.layout.item_spinner, spinnerItem)
        arrayAdapter.setDropDownViewResource(R.layout.item_spinner_text)
        binding.spinnerStoreRec.adapter = arrayAdapter


        binding.spinnerStoreRec.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    if (!mLoadingDialog.isShowing) getStoreRecList(position)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    private fun getStoreRecList(order: Int) {
        showLoadingDialog(this)
        StoreRecService(this).tryGetStoreRecList(getUserIdx(), getGroupIdx(), categoryIdx, order)
    }


    override fun onStoreClicked(storeIdx: Int) {
        val intent = Intent(this, CategoryStoreDetailActivity::class.java)
        intent.apply {
            putExtra("storeIdx", storeIdx)
        }
        startActivity(intent)
    }

    override fun onStoreLongClicked(storeName: String, storeImg : String) {
        val dialog = StoreToMateSuggestDialog(this, this, storeName, storeImg)
        dialog.show()
    }

    override fun onLikeClicked(storeIdx: Int, isLiked: Boolean) {
        if(isLiked) LikeService(this).tryPostLike(getUserIdx(), storeIdx)
        else LikeService(this).tryPatchLike(getUserIdx(), storeIdx)
    }

    override fun onGetStoreRecSuccess(response: StoreRecResponse) {
        dismissLoadingDialog()
        binding.llNoStoreRecResult.isVisible = false
        storeRecAdapter.removeAllData()
        storeRecAdapter.addAllData(response.result)
    }

    override fun onGetStoreRecFail(code: Int, message: String?) {
        dismissLoadingDialog()
        if(code == 2502){
            storeRecAdapter.removeAllData()
            binding.llNoStoreRecResult.isVisible = true
        }else {
            binding.llNoStoreRecResult.isVisible = false
            showCustomToast(message?:resources.getString(R.string.failed_connection))
        }
    }

    override fun onGotoMateSuggestClicked(storeName: String, storeImg: String) {
        val intent = Intent(this, MateSuggestionActivity::class.java)
        intent.apply {
            putExtra("storeName", storeName)
            putExtra("storeImg", storeImg)
        }
        startActivity(intent)
    }

    override fun onPostLikeSuccess() {

    }

    override fun onPostLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }

    override fun onPatchLikeSuccess() {
    }

    override fun onPatchLikeFail(message: String?) {
        showCustomToast(message?:resources.getString(R.string.failed_connection))
    }
}