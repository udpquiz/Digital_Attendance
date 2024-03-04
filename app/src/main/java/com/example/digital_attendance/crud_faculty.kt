package com.example.digital_attendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class crud_faculty(context: Context): SQLiteOpenHelper(context,"Attendance",null,1) {
    companion object{
        val TAB = "faculty"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TAB(f_code INTEGER PRIMARY KEY, f_name TEXT, f_email TEXT, f_password TEXT)")

    }

    fun insertFaculty(f_code: Int, f_name: String, f_email: String, f_password:String): Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("f_code",f_code)
        cv.put("f_name",f_name)
        cv.put("f_email",f_email)
        cv.put("f_password",f_password)
        val ins = db.insert(TAB,null,cv)
        return if (ins > 0)
            true
        else
            false
    }

    fun viewFaculty(): Cursor?
    {
        val db = this.writableDatabase
        return db.rawQuery("select * from $TAB",null)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


}


class crudCourse(context: Context): SQLiteOpenHelper(context,"Attendance1",null,1) {
    companion object{
        val TAB2 = "course"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TAB2(c_id INTEGER PRIMARY KEY, c_name TEXT, c_sem TEXT)")

    }

    fun insertCourse(c_id: Int, c_name: String, c_sem:String): Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("c_id",c_id)
        cv.put("c_name",c_name)
        cv.put("c_sem",c_sem)
        val ins = db.insert(TAB2,null,cv)
        return if (ins > 0)
            true
        else
            false
    }

    fun viewCourse(): Cursor?
    {
        val db = this.writableDatabase
        return db.rawQuery("select * from $TAB2",null)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}
