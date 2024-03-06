package com.example.digital_attendance
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext

class datahelper(context: Context):SQLiteOpenHelper(context,"DATABASE",null,4) {
//class datahelper(context: Context):SQLiteOpenHelper(context,"LJ",null,1) {
    companion object {
        const val TABLE_NAME = "Student_Login"
        const val col1 = "Enrollment_Number"
        const val col2 = "Password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table " + TABLE_NAME + "(Enrollment_Number TEXT,Password TEXT) ");
    }
    fun insertData(Enrollment_Number:String?,Password:String):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col1,Enrollment_Number)
        cv.put(col2,Password)
        val result = db.insert(TABLE_NAME,null,cv)
        return if(result<0)
            false
        else
            true
    }
    fun getAllData(): Cursor?{
        val db = this.writableDatabase
        return db.rawQuery("select * from $TABLE_NAME",null)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
