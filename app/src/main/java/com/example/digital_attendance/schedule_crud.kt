package com.example.digital_attendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class schedule_crud(context: Context): SQLiteOpenHelper(context,"data1",null,1) {



        companion object{
            val TAB="schedule"
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("create table $TAB(date TEXT,sem TEXT,division TEXT,start_time TEXT,end_time TEXT,sub_name TEXT,f_name TEXT,room TEXT)")

        }

        fun inserttt(date:String,sem:String,division:String,start_time:String,end_time:String,sub_name:String,f_name:String,room:String):Boolean
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
            val ins = db.insert(TAB,null,cv)
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
            return db.rawQuery("select * from $TAB", null)
        }
//    fun del():Int{
//        val db=this.writableDatabase
//        return db.delete("schedule",null,null) \\THIS DELETES ALL RECORDS OF TABLE
//    }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }


}
