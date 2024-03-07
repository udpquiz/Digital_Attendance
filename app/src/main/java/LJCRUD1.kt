package com.example.digital_attendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LJCRUD1(context: Context): SQLiteOpenHelper(context,"LJJ",null,3) {
    companion object{
        const val tab_faculty = "faculty"
        const val tab_course = "course"
        const val tab_studentlogin = "student_login"
        const val tab_schedule="schedule11"
        const val t="schedule1"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table faculty(f_code INTEGER PRIMARY KEY, f_name TEXT, f_email TEXT, f_password TEXT)")
        db?.execSQL("create table course(c_code INTEGER PRIMARY KEY,SEM INTEGER, NAME TEXT)")
        db?.execSQL("create table student_login(Enrollment_Number TEXT PRIMARY KEY,Password TEXT)")
        db?.execSQL("create table schedule11(date TEXT,sem TEXT,division TEXT,start_time TEXT,end_time TEXT,sub_name TEXT,f_name TEXT,room TEXT)")
    }
    fun insertFaculty(f_code: Int, f_name: String, f_email: String, f_password:String): Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("f_code",f_code)
        cv.put("f_name",f_name)
        cv.put("f_email",f_email)
        cv.put("f_password",f_password)
        val ins = db.insert(tab_faculty,null,cv)
        return if (ins > 0)
            true
        else
            false
    }
    fun viewFaculty(): Cursor?
    {
        val db = this.writableDatabase
        return db.rawQuery("select * from $tab_faculty",null)
    }

    fun addCourse(c_code:String?,sem:String?, name:String?):Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put("c_code",c_code)
        cv.put("SEM",sem)
        cv.put("NAME",name)

        val res = db.insert(tab_course,null,cv)
        return if (res > 0)
            true
        else
            false
    }
    fun viewCourse(): Cursor?
    {
        val db = this.writableDatabase
        return db.rawQuery("select * from $tab_course",null)
    }
    fun viewsub(sem:String?):Cursor?{
        val db = this.writableDatabase
        return db.rawQuery("select * from $tab_course where SEM=?", arrayOf(sem))
    }

    fun insertData(Enrollment_Number:String?,Password:String):Boolean{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(datahelper.col1,Enrollment_Number)
        cv.put(datahelper.col2,Password)
        val result = db.insert(tab_studentlogin,null,cv)
        return if(result<0)
            false
        else
            true
    }
    fun getAllData(): Cursor?{
        val db = this.writableDatabase
        return db.rawQuery("select * from $tab_studentlogin",null)
    }

    fun inserttt(
        date:String,
        sem:String,
        division:String,
        start_time:String,
        end_time:String,
        sub_name:String,
        f_name:String,
        room:String):Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("date",date)
        cv.put("sem",sem)
        cv.put("division",division)
        cv.put("start_time",start_time)
        cv.put("end_time",end_time)
        cv.put("sub_name",sub_name)
        cv.put("f_name",f_name)
        cv.put("room",room)
        val ins = db.insert(tab_schedule,null,cv)
        return if (ins > 0)
            true
        else
            false
    }
    //    fun viewsc(room: String):Cursor?{
    fun viewsc(): Cursor?{
//        val sem= arrayOf("2")
        val db=this.writableDatabase
//        Log.d("SQL_QUERY", "SELECT * FROM schedule WHERE room=$room")
//        return db.rawQuery("select * from $TAB where $room=?", arrayOf(room))
        return db.rawQuery("select * from $tab_schedule", null)
    }
    fun deleteSchedule(id: String): Int? {
        val db = this.writableDatabase
        return db.delete(tab_schedule, "schedule_Id = $id", null)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}