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
import kotlinx.android.synthetic.main.delegate_item_error.view.*
import java.util.*

/**
 * @author n.diazgranados
 */
class ErrorDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) =
        ErrorViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class ErrorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.delegate_item_loading)
    ) {
        private val message = itemView.da_error_message

        fun bind(item: EmptyItem) {
            item.value.let {
                message.text = itemView.context.getString(R.string.error_common_message)
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

class ErrorItem : ViewType {
    override fun getViewType() = AdapterViewType.ERROR
}