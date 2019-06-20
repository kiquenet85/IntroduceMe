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
import com.kiquenet.introduceme.view_models.model.Course
import kotlinx.android.synthetic.main.delegate_item_course.view.*
import java.util.*

/**
 * @author n.diazgranados
 */
class CourseDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CourseViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as CourseViewHolder
        holder.bind(item as CourseItem)
    }

    inner class CourseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.delegate_item_course)
    ) {

        private val titleCourseName = itemView.da_course_title_name
        private val courseName = itemView.da_course_item_name
        private val titleStartDate = itemView.da_course_title_start_date
        private val startDate = itemView.da_course_item_start_date
        private val titleEndDate = itemView.da_course_title_end_date
        private val endDate = itemView.da_course_item_end_date
        private val titleCertification = itemView.da_course_title_certification
        private val certificationLink = itemView.da_course_item_certification

        fun bind(item: CourseItem) {
            item.value.let {
                titleCourseName.text = "Course"
                courseName.text = it.name
                titleStartDate.text = "Start Date"
                startDate.text = it.startDate
                titleEndDate.text = "End Date"
                endDate.text = it.endDate
                titleCertification.text = "Certified in"
                certificationLink.text = it.certification
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applyJustificationOnText(
                    Arrays.asList(
                        titleCourseName,
                        courseName,
                        titleStartDate,
                        startDate,
                        titleEndDate,
                        endDate,
                        titleCertification,
                        certificationLink
                    )
                )
            }
        }
    }
}

data class CourseItem(val value: Course) : ViewType {
    override fun getViewType() = AdapterViewType.COURSES
}