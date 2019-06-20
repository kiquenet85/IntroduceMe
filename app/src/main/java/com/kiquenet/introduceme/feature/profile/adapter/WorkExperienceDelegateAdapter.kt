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
import com.kiquenet.introduceme.view_models.model.WorkExperience
import kotlinx.android.synthetic.main.delegate_item_work_exp.view.*
import java.util.*

/**
 * @author n.diazgranados
 */
class WorkExperienceDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return WorkExperienceViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as WorkExperienceViewHolder
        holder.bind(item as WorkExperienceItem)
    }

    inner class WorkExperienceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.delegate_item_work_exp)
    ) {

        private val jobTitle = itemView.da_work_exp_item_title
        private val duties = itemView.da_work_exp_item_duties
        private val titleStartDate = itemView.da_work_exp_title_start_date
        private val startDate = itemView.da_work_exp_item_start_date
        private val titleEndDate = itemView.da_work_exp_title_end_date
        private val endDate = itemView.da_work_exp_item_end_date

        fun bind(item: WorkExperienceItem) {
            item.value.let {
                jobTitle.text = it.name
                duties.text = it.duties ?: EMPTY_STRING
                titleStartDate.text = "StartDate"
                startDate.text = it.startDate ?: EMPTY_STRING
                titleEndDate.text = "EndDate"
                endDate.text = it.endDate ?: EMPTY_STRING
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applyJustificationOnText(
                    Arrays.asList(
                        jobTitle, duties,
                        titleStartDate, startDate,
                        titleEndDate, endDate
                    )
                )
            }
        }
    }
}

data class WorkExperienceItem(val value: WorkExperience) : ViewType {
    override fun getViewType() = AdapterViewType.WORK_EXPERIENCE
}