package com.example.ghichu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ghichu.DBase.DB_Content
import com.example.ghichu.Object.Note

class ContentActivity : AppCompatActivity() {
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var edt_content:EditText
    lateinit var edt_title:EditText
    lateinit var sql:DB_Content
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        edt_title=findViewById(R.id.edt_title_content)
        edt_content=findViewById(R.id.edt_content_content)
        toolbar=findViewById(R.id.toolbar_content)
        sql=DB_Content(this)
        setSupportActionBar(toolbar)
        //getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

//        var bundle: Bundle? =intent.getBundleExtra("bundle")
//        edt_title.setText(bundle?.getString("title"))
//        edt_content.setText(bundle?.getString("content"))
        val id=Integer.parseInt(getIntent().getStringExtra("id").toString().trim())
        edt_title.setText(sql.get1Note(id).getTitle())
        edt_content.setText(sql.get1Note(id).getContent())

    }
    //Button back
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            var intent:Intent= Intent(this@ContentActivity,MainActivity::class.java)
//            var bundle:Bundle=Bundle()
//            bundle.putString("title",edt_title.text.toString())
//            bundle.putString("content",edt_content.text.toString())
//            intent.putExtra("bundle",bundle)
           // Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
            val idNote=Integer.parseInt(getIntent().getStringExtra("id").toString().trim())
            var title:String=edt_title.text.toString()
            var content:String=edt_content.text.toString()

            sql.updateContent(Note(idNote,title, content))
            intent.putExtra("id",idNote)
            setResult(Activity.RESULT_OK,intent)
            finish()
            //startActivity(intent)
//            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
}