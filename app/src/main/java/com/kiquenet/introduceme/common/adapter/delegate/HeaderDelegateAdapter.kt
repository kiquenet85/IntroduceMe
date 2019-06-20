package com.kiquenet.introduceme.common.adapter.delegate

import android.os.Build
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.droidcba.kedditbysteps.commons.adapter.AdapterViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewTypeDelegateAdapter
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.util.applyJustificationOnText
import com.kiquenet.introduceme.util.inflate
import kotlinx.android.synthetic.main.delegate_item_header.view.*
import java.util.*

/**
 * @author n.diazgranados
 */
class HeaderDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) =
        HeaderViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as HeaderViewHolder
        holder.bind(item as HeaderItem)
    }

    class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.delegate_item_header)
    ) {
        private val titleName = itemView.da_header_title_name

        fun bind(item: HeaderItem) {
            item.value.let {
                titleName.text = it
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applyJustificationOnText(
                    Arrays.asList(
                        titleName
                    )
                )
            }
        }
    }
}

data class HeaderItem(val value: String) : ViewType {
    override fun getViewType() = AdapterViewType.HEADER
}