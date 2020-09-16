package com.zhuinden.simplestackdemoexamplemvp.features.tasks

import androidx.recyclerview.widget.DiffUtil

import com.zhuinden.simplestackdemoexamplemvp.data.models.Task

/**
 * Created by Zhuinden on 2018. 08. 20.
 */

class TasksDiffCallback(
    private val oldTasks: List<Task>?,
    private val newTasks: List<Task>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldTasks?.size ?: 0

    override fun getNewListSize(): Int = newTasks.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newTasks[newItemPosition].id == oldTasks!![oldItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newTasks[newItemPosition] == oldTasks!![oldItemPosition]
}