package com.myfittinglife.recyclerviewfilterable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

/**
@Author LD
@Time 2021/1/22 10:41
@Describe RecyclerView的Filterable使用，删选数据框使用
@Modify
 */
class MainActivity : AppCompatActivity() {


    companion object{
        const val TAG = "ceshi"
        const val CONDITION1 = "condition1"
        const val CONDITION2 = "condition2"
    }
    //过滤条件1
    var condition1 = ""
    //过滤条件2
    var condition2 = ""
    //总的过滤条件
    var jsonObject = JSONObject()

    private var dataList = mutableListOf<MyBean>()
    var myAdapter = MyAdapter(dataList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        jsonObject.put(CONDITION1,condition1)
        jsonObject.put(CONDITION2,condition2)

        initData()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = myAdapter
        //这里需要人为的筛选下，否则默认进来没有数据。这里屏蔽掉是因为mSpinner默认会选中第0项，我在onItemSelected()选中第0项时已经调用了
//        myAdapter.filter.filter(jsonObject.toString())



        /**
         * 商品类型选择
         */
        mSpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                myAdapter.filter.filter("")
                Log.i(TAG, "onNothingSelected: 过滤1没有选择")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    //默认会选择0
                    0 -> {
                        condition1=""
                    }
                    1 -> {
                        condition1="type1"
                    }
                    2 -> {
                        condition1="type2"
                    }
                    3 -> {
                        condition1="type3"
                    }
                    4 -> {
                        condition1="type4"
                    }
                }
                jsonObject.put(CONDITION1,condition1)
                myAdapter.filter.filter(jsonObject.toString())
            }
        }
        /**
         * 配送方式选择
         */
        mSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                myAdapter.filter.filter("")
                Log.i(TAG, "onNothingSelected: 过滤2没有选择")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        condition2=""
                    }
                    1 -> {
                        condition2="同城配送"
                    }
                    2->{
                        condition2="普通快递"
                    }
                }
                jsonObject.put(CONDITION2,condition2)
                myAdapter.filter.filter(jsonObject.toString())
            }
        }

    }


    /**
     * 初始化数据
     */
    private fun initData() {
        dataList.add(MyBean("type1", "草莓","同城配送"))
        dataList.add(MyBean("type1", "香蕉","普通快递"))
        dataList.add(MyBean("type1", "西瓜","同城配送"))
        dataList.add(MyBean("type1", "西柚","普通快递"))
        dataList.add(MyBean("type1", "梨","同城配送"))
        dataList.add(MyBean("type2", "大葱","普通快递"))
        dataList.add(MyBean("type2", "西红柿","同城配送"))
        dataList.add(MyBean("type2", "土豆","普通快递"))
        dataList.add(MyBean("type2", "南瓜","同城配送"))
        dataList.add(MyBean("type2", "胡萝卜","普通快递"))
        dataList.add(MyBean("type3", "百事可乐","同城配送"))
        dataList.add(MyBean("type3", "元气森林","普通快递"))
        dataList.add(MyBean("type3", "雪碧","同城配送"))
        dataList.add(MyBean("type3", "汇源果汁","普通快递"))
        dataList.add(MyBean("type3", "贵州茅台","同城配送"))
        dataList.add(MyBean("type4", "洽洽瓜子","普通快递"))
        dataList.add(MyBean("type4", "旺旺小小酥","同城配送"))
        dataList.add(MyBean("type4", "浪味仙","普通快递"))
        dataList.add(MyBean("type4", "上好佳鲜虾条","同城配送"))
        dataList.add(MyBean("type4", "威龙大面筋","普通快递"))
    }
}