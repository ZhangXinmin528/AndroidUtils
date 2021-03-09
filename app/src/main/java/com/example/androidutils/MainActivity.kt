package com.example.androidutils

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.androidutils.fragment.*
import com.example.androidutils.adapter.NavigationAdapter
import com.example.androidutils.base.BaseActivity
import com.example.androidutils.bean.NaviEntity
import com.example.androidutils.listener.OnItemClickListener

class MainActivity : BaseActivity(), OnItemClickListener {

    private val mDataList: MutableList<NaviEntity>? = ArrayList()

    private var mAdapter: NavigationAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParamsAndValues() {

        mAdapter = NavigationAdapter(mContext, mDataList)
    }

    override fun initViews() {
//        mRecyclerView = findViewById(R.id.rv_home)
//        mRecyclerView.setAdapter(mAdapter)
//        val layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
//        mRecyclerView.setLayoutManager(layoutManager)
//        mRecyclerView.addItemDecoration(
//                StaggeredItemDecoration(mContext))
//        mAdapter!!.setOnItemClickListener(this)
    }

    protected fun initData() {
        val temp = resources.getStringArray(R.array.sort_name_array)
        val iconArray = resources.obtainTypedArray(R.array.sort_icon_array)
        val length = temp.size
        for (i in 0 until length) {
            val entity = NaviEntity()
            entity.name = temp[i]
            val iconId = iconArray.getResourceId(i, -1)
            entity.iconId = iconId
            mDataList!!.add(entity)
        }
        mAdapter!!.notifyDataSetChanged()
        iconArray.recycle()
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View, position: Int) {
        val intent = Intent()
        when (position) {
            0 -> intent.setClass(mContext!!, DeviceFragment::class.java)
            1 -> intent.setClass(mContext!!, DialogFragment::class.java)
            2 -> intent.setClass(mContext!!, EncryptFragment::class.java)
            3 -> intent.setClass(mContext!!, ImageFragment::class.java)
            4 -> intent.setClass(mContext!!, KeyboradFragment::class.java)
            5 -> intent.setClass(mContext!!, LogFragment::class.java)
            6 -> intent.setClass(mContext!!, NetWatcherFragment::class.java)
            7 -> {
                //                intent.setClass(mContext, NetWatcherActivity.class);
//                break;
                Toast.makeText(mContext, "暂未开放~", Toast.LENGTH_SHORT).show()
                return
            }
            8 -> intent.setClass(mContext!!, PermissionFragment::class.java)
            9 -> intent.setClass(mContext!!, PhoneFragment::class.java)
            10 -> intent.setClass(mContext!!, PingFragment::class.java)
            11 -> intent.setClass(mContext!!, SpanFragment::class.java)
            12 -> intent.setClass(mContext!!, ScreenFragment::class.java)
            13 -> {
                //                intent.setClass(mContext, PingActivity.class);
//                break;
                Toast.makeText(mContext, "暂未开放~", Toast.LENGTH_SHORT).show()
                return
            }
            14 -> intent.setClass(mContext!!, PaletteFrameng::class.java)
            15 -> intent.setClass(mContext!!, SettingFragment::class.java)
            16 -> intent.setClass(mContext!!, CrashFragment::class.java)
            else -> {
            }
        }
        startActivity(intent)
    }
}