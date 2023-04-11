package com.example.ghichu

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghichu.Adapter.AdapterRcv
import com.example.ghichu.DBase.DB_Content
import com.example.ghichu.Object.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var sql:DB_Content
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var listItem:ArrayList<Note>
    lateinit var adapterRcv: AdapterRcv
    lateinit var rcv: RecyclerView
    lateinit var btnaddnew:FloatingActionButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sql= DB_Content(this)
        toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        btnaddnew=findViewById(R.id.btn_addnew)
        rcv=findViewById(R.id.rcv_main)
        listItem= ArrayList()

        //sql.deleteAllData()
        FillTable()
//        // Button Add
        btnaddnew.setOnClickListener(View.OnClickListener {
            var intent: Intent =Intent(this@MainActivity,ContentActivity::class.java)
            //Create a note
            val id=sql.getIdNote()
            Toast.makeText(this,id.toString(),Toast.LENGTH_SHORT).show()
            var note:Note=Note(id,"","")
            sql.addNote(note)
            intent.putExtra("id",id.toString())
            startActivityForResult(intent,1)
        })
//
    }
//    override   fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.btn_add -> {
//                sql.updateContent(Note(1,"aaa","Bui huu NGhjia"))
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            FillTable()
            Toast.makeText(this@MainActivity,"OK1",Toast.LENGTH_SHORT).show()
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            FillTable()
            Toast.makeText(this@MainActivity,"OK1",Toast.LENGTH_SHORT).show()
        }
    }
    fun FillTable(){
        adapterRcv=AdapterRcv(sql.getNote())
        rcv.adapter=adapterRcv
        rcv.layoutManager= GridLayoutManager(this,2)
        adapterRcv.setOnClick(object :RcvOnClick{
            override fun ClickItem(position: Int) {
                var intent: Intent =Intent(this@MainActivity,ContentActivity::class.java)
                intent.putExtra("id",sql.getNote()[position].getId().toString())
                startActivityForResult(intent,2)
            }
        })
        adapterRcv.setOnLongClick(object :RcvOnClick{
            override fun LongClickItem(position: Int) {
                val id:Int=sql.getNote()[position].getId()
                dialog(id)
            }
        })
    }
////    fun addList(){
////        sql.addNote(Note("Nghia Junior","01-03"))
////    }
    fun dialog(id:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Xoa ghi chu")
        builder.setMessage("ban co thuc su muon xóa ?")
        builder.setPositiveButton("OK") { dialog, which ->
            sql.deleteNote(id)
            FillTable()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // Do something when "Cancel" button is pressed
        }
        val dialog = builder.create()
        dialog.show()
    }
}