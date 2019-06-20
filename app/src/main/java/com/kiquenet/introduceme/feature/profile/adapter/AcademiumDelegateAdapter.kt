package com.kiquenet.introduceme.feature.profile.adapter

import android.os.Build
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.droidcba.kedditbysteps.commons.adapter.AdapterViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewType
import com.droidcba.kedditbysteps.commons.adapter.ViewTypeDelegateAdapter
import com.kiquenet.introduceme.R
import com.kiquenet.introduceme.util.applyJustificationOnText
import com.kiquenet.introduceme.util.inflate
import com.kiquenet.introduceme.common.view_models.model.Academium
import kotlinx.android.synthetic.main.delegate_item_academium.view.*
import java.util.*

/**
 * @author n.diazgranados
 */
class AcademiumDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return AcademiumViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AcademiumViewHolder
        holder.bind(item as AcademiumItem)
    }

    inner class AcademiumViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.delegate_item_academium)
    ) {

        private val institutionTitle = itemView.da_academium_item_title
        private val courseName = itemView.da_academium_item_name

        fun bind(item: AcademiumItem) {
            item.value.let {
                institutionTitle.text = "Institution Name"
                courseName.text = it.name
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applyJustificationOnText(
                    Arrays.asList(
                        institutionTitle, courseName
                    )
                )
            }
        }
    }
}

data class AcademiumItem(val value: Academium) : ViewType {
    override fun getViewType() = AdapterViewType.ACADEMIUM
}