package com.example.ghichu.Object
class Note{
    private var title:String=""
    private var content:String=""
    private var id:Int=0

    constructor(){

    }
    constructor(id: Int){
        this.id=id
    }
    constructor(id: Int,title:String,content:String){
        this.id=id
        this.title=title
        this.content=content
    }
    fun getTitle():String{
        return title
    }
    fun getContent():String{
        return content
    }
    fun setTitle(title: String){
        this.title=title
    }
    fun setContent(content: String){
        this.content=content
    }
    fun getId():Int{return id}
    fun setId(id:Int){this.id=id}







}
