package com.example.digital_attendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Crud_Course(context: Context): SQLiteOpenHelper(context,"Attendance2",null,1) {
    companion object{
        const val courseTab = "course"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table course(c_code INTEGER PRIMARY KEY,SEM INTEGER, NAME TEXT)")
    }

    fun addCourse(c_code:String?,sem:String?, name:String?):Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("c_code",c_code)
        cv.put("SEM",sem)
        cv.put("NAME",name)

        val res = db.insert(courseTab,null,cv)
        return if (res > 0)
            true
        else
            false
    }


    fun viewCourse(): Cursor?
    {
        val db = this.writableDatabase
        return db.rawQuery("select * from $courseTab",null)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


}