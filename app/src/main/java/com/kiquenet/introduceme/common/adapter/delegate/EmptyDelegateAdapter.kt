package com.kiquenet.introduceme.common.adapter.delegate

import android.os.Build
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.droidcba.kedditbysteps.commons.adapter.AdapterViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewTypeDelegateAdapter
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.util.EMPTY_STRING
import com.kiquenet.introduceme.util.applyJustificationOnText
import com.kiquenet.introduceme.util.inflate
import kotlinx.android.synthetic.main.delegate_item_empty.view.*
import java.util.*

/**
 * @author n.diazgranados
 * Empty common delegate adapter.
 */
class EmptyDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) =
        EmptyViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as EmptyViewHolder
        holder.bind(item as EmptyItem)
    }

    class EmptyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.delegate_item_empty)
    ) {
        private val message = itemView.da_empty_message

        fun bind(item: EmptyItem) {
            item.value.let {
                message.text = itemView.context.getString(R.string.empty_common_message)
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applyJustificationOnText(
                    Arrays.asList(
                        message
                    )
                )
            }
        }
    }
}

data class EmptyItem(val value: String = EMPTY_STRING) : ViewType {
    override fun getViewType() = AdapterViewType.EMPTY
}