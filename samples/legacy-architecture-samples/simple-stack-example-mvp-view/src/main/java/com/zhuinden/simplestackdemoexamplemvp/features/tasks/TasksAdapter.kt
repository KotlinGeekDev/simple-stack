package com.zhuinden.simplestackdemoexamplemvp.features.tasks

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhuinden.simplestackdemoexamplemvp.R
import com.zhuinden.simplestackdemoexamplemvp.data.models.Task
import com.zhuinden.simplestackdemoexamplemvp.util.Preconditions.checkNotNull
import com.zhuinden.simplestackdemoexamplemvp.util.inflate
import com.zhuinden.simplestackdemoexamplemvp.util.onClick
import java.util.*

class TasksAdapter(
    private var tasks: List<Task>,
    private val itemListener: TaskItemListener
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    var data: List<Task>
        get() = Collections.unmodifiableList(tasks)
        set(tasks) {
            this.tasks = checkNotNull(tasks)
        }

    class TaskViewHolder(
        private val containerView: View,
        private val itemListener: TaskItemListener
    ) : RecyclerView.ViewHolder(containerView) {
        private val title = containerView.findViewById<TextView>(R.id.title)
        private val complete = containerView.findViewById<CheckBox>(R.id.complete)

        lateinit var task: Task

        private val rowClickListener = View.OnClickListener { _ -> itemListener.onTaskRowClicked(task) }

        private val context = containerView.context

        init {
            containerView.setOnClickListener(rowClickListener)
            containerView.findViewById<View>(R.id.complete).onClick {
                itemListener.onTaskCheckClicked(task)
            }
        }

        fun bind(task: Task) {
            this.task = task
            title.text = task.titleForList
            complete.isChecked = task.isCompleted
            containerView.setBackgroundResource(when {
                task.isCompleted -> R.drawable.list_completed_touch_feedback
                else -> R.drawable.touch_feedback
            })
        }
    }

    interface TaskItemListener {
        fun onTaskRowClicked(task: Task)

        fun onTaskCheckClicked(task: Task)
    }

    init {
        data = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(parent.inflate(R.layout.task_item), itemListener)

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size
}

