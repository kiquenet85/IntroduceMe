package com.droidcba.kedditbysteps.commons.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author n.diazgranados
 */
interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}