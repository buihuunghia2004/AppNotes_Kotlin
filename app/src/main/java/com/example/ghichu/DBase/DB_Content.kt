package com.example.ghichu.DBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ghichu.Object.Note

class DB_Content(context:Context):SQLiteOpenHelper(context, TABLE_NAME,null,1) {
    //var list:ArrayList<Note> = ArrayList()
    companion object{
        const val TABLE_NAME="notes"
        const val COLUMN_TITLE="title"
        const val COLUMN_CONTENT="content"
        const val COLUMN_ID="id"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query:String=String.format("CREATE TABLE %s (%s INTEGER, %s TEXT,%s TEXT)", TABLE_NAME,
            COLUMN_ID,
            COLUMN_TITLE, COLUMN_CONTENT)
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query:String=String.format("DROP TABLE IF EXISTS %s", TABLE_NAME)
        db?.execSQL(query)
    }
    fun addNote(note: Note):Long{
        val db=this.writableDatabase
        var value =ContentValues()
        value.put(COLUMN_ID,note.getId())
        value.put(COLUMN_TITLE,note.getTitle())
        value.put(COLUMN_CONTENT,note.getContent())
        val result=db.insert(TABLE_NAME,null,value)
        db.close()
        return result
    }
    fun getNote():ArrayList<Note>{
        val list:ArrayList<Note> = ArrayList<Note>()
        val db=this.readableDatabase
        val cursor: Cursor =db.rawQuery("SELECT * FROM ${TABLE_NAME}",null)
        if (cursor.moveToFirst()){
            do {
                val IndexId=cursor.getColumnIndex(COLUMN_ID)
                val IndexTitle=cursor.getColumnIndex(COLUMN_TITLE)
                val IndexContent=cursor.getColumnIndex(COLUMN_CONTENT)

                val note:Note= Note(
                    cursor.getInt(IndexId),
                    cursor.getString(IndexTitle),
                    cursor.getString(IndexContent)
                )
                list.add(note)
            }while (cursor.moveToNext())
        }
        db.close()
        return list
    }
    fun updateContent(note: Note): Int {
        val db=this.writableDatabase
        val value=ContentValues()
        value.put(COLUMN_TITLE,note.getTitle())
        value.put(COLUMN_CONTENT,note.getContent())
        val whereClause=String.format("%s = %s", COLUMN_ID,note.getId())
        val result=db.update(TABLE_NAME,value,whereClause,null)
        db.close()
        return result
    }
    fun getIdNote():Int{
        val list:ArrayList<Note> = getNote()
        val myArray = Array(list.size) { 0 }
        for (i in 0 until list.size){
            myArray[i]=list.get(i).getId()
        }
        myArray.sort()
        if(list.size == 0){
            return 0
        }else{
            for(i in 0 .. list.size){
                if(i == list.size){
                    return i
                }
                if (i != myArray[i]){
                    return i
                }
            }
            return 1
        }
    }
    fun deleteAllData() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }fun get1Note(id :Int):Note{
        var note:Note=Note()
        val db=this.readableDatabase
        val cursor:Cursor=db.rawQuery("SELECT * FROM ${TABLE_NAME} WHERE ${COLUMN_ID} = ${id}",null)
        if (cursor.moveToFirst()){
            do {
                val id=cursor.getColumnIndex(COLUMN_ID)
                val title=cursor.getColumnIndex(COLUMN_TITLE)
                val content=cursor.getColumnIndex(COLUMN_CONTENT)
                note= Note(cursor.getInt(id),cursor.getString(title),cursor.getString(content))
            }while (cursor.moveToNext())
        }
        db.close()
        return note
    }
    fun deleteNote(id: Int):Int{
        val db=this.writableDatabase
        val whereClause=String.format("%s = %s", COLUMN_ID,id)
        val result=db.delete(TABLE_NAME,whereClause,null)
        db.close()
        return result
    }

}