package com.example.wishlist
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val wListItem : MutableList<Wishlist> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wListRV = findViewById<RecyclerView>(R.id.wishlistRV)

        val adapter = WishlistAdapter(wListItem)

        wListRV.adapter = adapter
        wListRV.layoutManager = LinearLayoutManager(this)

        val submitButton = findViewById<Button>(R.id.submitButton)
        val itemNameText = findViewById<EditText>(R.id.item_name_Input)
        val itemPriceText = findViewById<EditText>(R.id.item_price_Input)
        val urlLocationText = findViewById<EditText>(R.id.item_url_Input)

        adapter.setOnWishlistItemLongClickListener(object : WishlistAdapter.OnWishlistItemLongClickListener {
            override fun onWishlistItemLongClick(position: Int) {
                adapter.removeItem(position)
                Toast.makeText(getApplicationContext(), "Item removed from WishList", Toast.LENGTH_SHORT).show()
            }
        })
        submitButton.setOnClickListener {
            wListItem.add(Wishlist(itemNameText.text.toString(),urlLocationText.text.toString(), "$"+ itemPriceText.text.toString ()))

            itemNameText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            itemPriceText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            urlLocationText.onEditorAction(EditorInfo.IME_ACTION_DONE)

            itemNameText.text = null
            itemPriceText.text = null
            urlLocationText.text = null

            Toast.makeText(getApplicationContext(), "Item Added to WishList", Toast.LENGTH_SHORT).show()

            adapter.notifyDataSetChanged()
        }
    }
}
