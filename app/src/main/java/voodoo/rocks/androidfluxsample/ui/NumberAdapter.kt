package voodoo.rocks.androidfluxsample.ui

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_number.*
import voodoo.rocks.androidfluxsample.R

fun numberAdapter(onClick: (index: Int) -> Unit) = AsyncListDifferDelegationAdapter(diffUtil,
    adapterDelegateLayoutContainer<Int, Int>(R.layout.item_number) {

        numberItem.setOnClickListener { onClick(adapterPosition) }

        bind {
            numberItem.text = getString(R.string.numberItem, item)
        }
    })

private val diffUtil = object : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

}