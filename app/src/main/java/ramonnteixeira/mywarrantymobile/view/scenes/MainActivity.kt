package ramonnteixeira.mywarrantymobile.view.scenes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.warranty_list.*
import ramonnteixeira.mywarrantymobile.R
import ramonnteixeira.mywarrantymobile.controller.WarrantyController
import ramonnteixeira.mywarrantymobile.model.entity.Warranty
import ramonnteixeira.mywarrantymobile.view.adapters.ItemWarrantyAdapter

class MainActivity(
    private val warrantyList: MutableList<Warranty> = mutableListOf()
) : AppCompatActivity() {

    private val requestEditWarranty= 1

    private var controller: WarrantyController? = null
    private var refreshFunc: () -> List<Warranty> = { arrayListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        controller = WarrantyController(applicationContext)
        refreshFunc = { controller!!.findActives() }

        createWarrantyButton.setOnClickListener { createWarranty() }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        warranties.layoutManager = LinearLayoutManager(this)
        warranties.adapter = ItemWarrantyAdapter(warrantyList)

        refresh()
    }

    private fun refresh() {
        warrantyList.clear()
        warrantyList.addAll(refreshFunc())
        warranties.adapter?.notifyDataSetChanged()
        emptySpaceWarranties.visibility = if (warrantyList.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        try {
            if (controller == null) {
                return@OnNavigationItemSelectedListener true
            }

            when (item.itemId) {
                R.id.navigation_actives -> {
                    refreshFunc = { controller!!.findActives() }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_expired -> {
                    refreshFunc = { controller!!.findExpired() }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_all -> {
                    refreshFunc = { controller!!.findAll() }
                    return@OnNavigationItemSelectedListener true
                }
            }
        } finally {
            refresh()
        }
        false
    }

    private fun createWarranty() {
        val intent = Intent(this, WarrantyEditActivity::class.java)
        startActivityForResult(intent, requestEditWarranty)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == requestEditWarranty) {
            Toast.makeText(this, R.string.msg_warranty_created, Toast.LENGTH_LONG).show()
            refresh()
        }
    }

}
