package com.myfittinglife.recyclerviewfilterable


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject


/**
 * @Author LD
 * @Time 2021/1/20 16:29
 * @Describe Filterable使用，有效
 * @Modify
 */
class MyAdapter(data: MutableList<MyBean>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>(), Filterable {

    companion object {
        const val TAG = "ceshi"
    }

    //存放原数据
    private var mSourceList = mutableListOf<MyBean>()

    //存放过滤后的数据
    private var mFilterList = mutableListOf<MyBean>()


    init {
        mSourceList = data
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = mFilterList[position].name
        holder.deliverType.text = mFilterList[position].deliverType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    /**
     * 注意：这里返回过滤后的集合大小
     */
    override fun getItemCount(): Int {
        Log.i(TAG, "getItemCount: 数量为" + mFilterList.size)
        return mFilterList.size
    }


    
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //商品名称
        var name: TextView = itemView.findViewById(R.id.tvName)

        //配送方式
        var deliverType: TextView = itemView.findViewById(R.id.tvDeliverType)
    }

    /**
     * 具体的执行过滤的操作
     * 创建适配器后会默认的执行一次
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            //执行过滤操作
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                Log.i(TAG, "performFiltering: 执行过滤操作，过滤字段为：$charString")

                val jsonObject = JSONObject(charString)
                //筛选条件一
                var condition1 = jsonObject.getString("condition1")
                //筛选条件二
                var condition2 = jsonObject.getString("condition2")

                //临时的用于两个都不为空的操作

                //存放已过滤的数据
                var theFilterList = if (condition1.isEmpty() && condition2.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mSourceList
                } else if (condition2.isEmpty()) {
                    mSourceList.filter { it.type == condition1 }

                } else if (condition1.isEmpty()) {
                    mSourceList.filter { it.deliverType == condition2 }

                } else {
                    mSourceList.filter { it.type == condition1 && it.deliverType == condition2 }
                }
                val filterResults = FilterResults()
                filterResults.values = theFilterList
                return filterResults
            }

            //把过滤后的值返回出来并进行更新
            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                mFilterList = filterResults.values as MutableList<MyBean>
                notifyDataSetChanged()
            }
        }
    }
}