package com.harshit.kotlin_mvvm_retrofit_coroutines.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harshit.kotlin_mvvm_retrofit_coroutines.data.model.User
import com.harshit.kotlin_mvvm_retrofit_coroutines.databinding.ItemLayoutBinding

class MainAdapter(private val users: ArrayList<User>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: ItemLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val binding = ItemLayoutBinding.bind(itemView.root)

        fun bind(user: User) {
            itemView.apply {
                binding.textViewUserName.text = user.name
                binding.textViewUserEmail.text = user.email
                Glide.with(binding.imageViewPhoto.context)
                    .load(user.photo)
                    .into(binding.imageViewPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        //DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(users[position])
        }
    }

    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }

    fun addUsers(users: List<User>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }
}