package com.example.androidutils.fragment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.androidutils.R
import com.example.androidutils.model.FuncItemDescription

/**
 * Created by ZhangXinmin on 2021/3/16.
 * Copyright (c) 2021 . All rights reserved.
 */
class HomeItemAdapter(dataList: MutableList<FuncItemDescription>) :
    BaseQuickAdapter<FuncItemDescription, BaseViewHolder>(
        data = dataList,
        layoutResId = R.layout.layout_navigation_list_item
    ) {

    override fun convert(holder: BaseViewHolder, item: FuncItemDescription) {
        holder.setImageResource(R.id.iv_item_icon, item.iconRes)
            .setText(R.id.tv_item_name, item.name)
    }
}