package ramonnteixeira.mywarrantymobile.view.scenes

import android.os.Bundle
import android.view.View
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

    private var controller: WarrantyController? = null
    private var refreshFunc: () -> List<Warranty> = { arrayListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        controller = WarrantyController(baseContext)
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
                    refreshFunc = { controller!!.findExpireds() }
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

//    private fun createTaskDialog() {
//        val builder = AlertDialog.Builder(this)
//        val view = layoutInflater.inflate(R.layout.dialog_task, null)
//        builder
//            .setView(view)
//            .setPositiveButton(getString(R.string.button_add)) { dialog, _ ->
//                val lastTask = createTask(view.taskNameEdit.text.toString())
//                Snackbar
//                    .make(view, "Task created with success", Snackbar.LENGTH_LONG)
//                    .setAction("Undo") { remove(lastTask) }
//                    .show()
//                dialog.dismiss()
//            }
//            .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ -> dialog.dismiss() }
//
//        builder.create().show()
//    }
//    private fun createTask(name: String): Task {
//        val task = getController().create(name)
//        refresh()
//        return task
//    }
//
//    private fun remove(task: Task) {
//        getController().remove(task)
//        refresh()
//    }

}
