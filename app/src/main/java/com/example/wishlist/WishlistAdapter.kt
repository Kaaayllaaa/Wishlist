package com.example.wishlist

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistAdapter(private val items: MutableList<Wishlist>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemNameView: TextView
        val itemPriceView: TextView
        val urlView: TextView

        init {
            itemNameView = itemView.findViewById(R.id.itemName)
            itemPriceView = itemView.findViewById(R.id.itemPrice)
            urlView = itemView.findViewById(R.id.itemLocation)
        }
    }
    interface OnWishlistItemLongClickListener {
        fun onWishlistItemLongClick(position: Int)
    }

    private var onWishlistItemLongClickListener: OnWishlistItemLongClickListener? = null

    fun setOnWishlistItemLongClickListener(listener: OnWishlistItemLongClickListener) {
        onWishlistItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.wishlist_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemNameView.text = item.itemName
        holder.itemPriceView.text = item.itemPrice
        holder.urlView.text = item.urlWebsite

        holder.itemView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(item.urlWebsite))
                    withContext(Dispatchers.Main) {
                        ContextCompat.startActivity(it.context, browserIntent, null)
                    }
                } catch (e: ActivityNotFoundException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            it.context,
                            "Invalid URL for " + item.itemName,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        holder.itemView.setOnLongClickListener {
            onWishlistItemLongClickListener?.onWishlistItemLongClick(position)
            true
        }
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun get(position: Int): Wishlist {
        return items[position]
    }
}
