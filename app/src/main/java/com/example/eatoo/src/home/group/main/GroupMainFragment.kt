package com.example.eatoo.src.home.group.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.eatoo.R
import com.example.eatoo.config.ApplicationClass.Companion.GROUP_IDX
import com.example.eatoo.config.BaseFragment
import com.example.eatoo.databinding.FragmentGroupMainBinding
import com.example.eatoo.src.home.adapter.Home_Group_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.main.adapter.Group_Home_Main_Mate_Kind_RecyclerviewAdapter
import com.example.eatoo.src.home.group.main.model.GroupMainResponse
import com.example.eatoo.util.getGroupIdx
import com.example.eatoo.util.getUserIdx

class GroupMainFragment : BaseFragment<FragmentGroupMainBinding>(FragmentGroupMainBinding::bind, R.layout.fragment_group_main) ,GroupMainView {


    val GroupIdx = getGroupIdx()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GroupMainService(this).tryGetGroupMain(getUserIdx(),GroupIdx)

    }

    override fun onGetGroupMainSuccess(response: GroupMainResponse) {
        showCustomToast("요청 완료")
        val GroupAdapter = Group_Home_Main_Mate_Kind_RecyclerviewAdapter(response.result.getMateRes)
        binding.findingMateRecyclerview.adapter = GroupAdapter
        binding.findingMateRecyclerview.layoutManager = LinearLayoutManager(activity).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }

        if(response.result.getStoreRes.size > 0){
            var imgUrl : String = ""

            if(response.result.getStoreRes[0].imgUrl != null){
                imgUrl = response.result.getStoreRes[0].imgUrl
                Glide.with(this).load(imgUrl).into(binding.storeImg)
                binding.storeImg.clipToOutline = true
            }

            binding.storeNameTv.text = response.result.getStoreRes[0].storeName
            binding.storeLocationTv.text = response.result.getStoreRes[0].address
            binding.storeScoreTv.text = response.result.getStoreRes[0].rating.toString()

            binding.storeList2.visibility = View.GONE

            if(response.result.getStoreRes.size > 1){
                binding.storeList2.visibility = View.VISIBLE
                var imgUrl2 : String = ""
                if(response.result.getStoreRes[1].imgUrl != null){
                    imgUrl2 = response.result.getStoreRes[1].imgUrl
                    Glide.with(this).load(imgUrl2).into(binding.storeImg2)
                    binding.storeImg2.clipToOutline = true
                }
                binding.storeNameTv2.text = response.result.getStoreRes[1].storeName
                binding.storeLocationTv2.text = response.result.getStoreRes[1].address
                binding.storeScoreTv2.text = response.result.getStoreRes[1].rating.toString()
            }

        }
        else{
            binding.storeList1.visibility = View.GONE
            binding.storeList2.visibility = View.GONE
        }

    }

    override fun onGetGroupMainFail(message: String?) {
    }
}