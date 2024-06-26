    package com.example.digital_attendance
    import android.content.ContentValues
    import android.content.Context
    import android.database.Cursor
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper
    import com.example.digital_attendance.Schedule1
    import com.example.digital_attendance.datahelper

    class LJCRUD1(context: Context): SQLiteOpenHelper(context,"LJ_Crud",null,14) {
        companion object{
            const val tab_faculty = "faculty"
            const val tab_course = "course"
            const val tab_studentlogin = "student_login"
            const val tab_schedule="schedule11"
            const val tab_room="room"
            const val tab_division="division"
            const val t="schedule1"
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("create table faculty(f_code INTEGER PRIMARY KEY, f_name TEXT, f_email TEXT, f_password TEXT)")
            db?.execSQL("create table course(c_code INTEGER PRIMARY KEY,SEM INTEGER, NAME TEXT)")
            db?.execSQL("create table student_login(Enrollment_Number TEXT PRIMARY KEY,Password TEXT)")
            db?.execSQL("create table schedule11(schedule_id INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,sem TEXT,division TEXT,start_time TEXT,end_time TEXT,sub_name TEXT,f_name TEXT,room TEXT,LEC_NO TEXT)")
            db?.execSQL("create table room(r_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)")
            db?.execSQL("create table division(div_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)")

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
            date: String,
            sem: String,
            division: String,
            start_time: String,
            end_time: String,
            sub_name: String,
            f_name: String,
            room: String,
            LEC_NO: String
        ):Boolean
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
            cv.put("LEC_NO",LEC_NO)
            val ins = db.insert(tab_schedule,null,cv)
            return if (ins > 0)
                true
            else
                false
        }
        fun viewsc(date: String?): Cursor?{
    //        val sem= arrayOf("2")
            val db=this.writableDatabase
            return db.rawQuery("select * from $tab_schedule WHERE date='$date'", null)
        }
        fun viewallsc(): Cursor?{
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
        fun updateSchedule(schedule: Schedule1): Boolean {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put("date", schedule.date)
            cv.put("sem", schedule.sem)
            cv.put("division", schedule.division)
            cv.put("start_time", schedule.start_time)
            cv.put("end_time", schedule.end_time)
            cv.put("sub_name", schedule.sub_name)
            cv.put("f_name", schedule.f_name)
            cv.put("room", schedule.room)
            cv.put("LEC_NO", schedule.LEC_NO)

            val whereClause = "schedule_id = ?"
            val whereArgs = arrayOf(schedule.id)
            val updatedRows = db.update(tab_schedule, cv, whereClause, whereArgs)

            return updatedRows > 0
        }
        fun faculty_schedule(name:String?,date: String?):Cursor?{
            val db = this.writableDatabase
            return db.rawQuery("Select * from $tab_schedule WHERE f_name='$name' AND date='$date'",null)
        }
        fun Attendance_subject(sem: Int?):Cursor{
            val db = this.writableDatabase
            return db.rawQuery("Select * from course where SEM='$sem'",null)
        }
        fun addroom(name:String?):Boolean
        {
            val db = this.writableDatabase
            val cv = ContentValues()

            cv.put("NAME",name)

            val res = db.insert(tab_room,null,cv)
            return if (res > 0)
                true
            else
                false
        }
        fun viewroom(): Cursor?{
            val db=this.writableDatabase
            return db.rawQuery("select * from $tab_room", null)
        }
        fun adddivision(name:String?):Boolean
        {
            val db = this.writableDatabase
            val cv = ContentValues()

            cv.put("NAME",name)

            val res = db.insert(tab_division,null,cv)
            return if (res > 0)
                true
            else
                false
        }
        fun viewdiv(): Cursor?{
            val db=this.writableDatabase
            return db.rawQuery("select * from $tab_division", null)
        }
        fun checkDivisionExists(name: String?): Boolean {
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $tab_division WHERE NAME=?", arrayOf(name))
            val divisionExists = cursor.count > 0
            cursor.close()
            return divisionExists
        }

        fun checkRoomExists(name: String?): Boolean {
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM $tab_room WHERE NAME=?", arrayOf(name))
            val roomExists = cursor.count > 0
            cursor.close()
            return roomExists
        }

        fun student_schedule(date: String?,sem:String?,div:String?):Cursor?{
            val db = this.writableDatabase
            return db.rawQuery("Select * from $tab_schedule WHERE date='$date' AND sem='$sem' AND division='$div'",null)
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            if (oldVersion < newVersion) {
    //            db?.execSQL("create table IF NOT EXISTS room(r_id INTEGER PRIMARY KEY,NAME TEXT)")
    //            db?.execSQL("create table IF NOT EXISTS division(div_id INTEGER PRIMARY KEY,NAME TEXT)")
    //            db?.execSQL("CREATE TABLE IF NOT EXISTS division_temp(div_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)")
    //            db?.execSQL("DROP TABLE division")
    //            db?.execSQL("ALTER TABLE division_temp RENAME TO division")
//                db?.execSQL("CREATE TABLE IF NOT EXISTS room_temp(r_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)")
//                db?.execSQL("DROP TABLE room")
//                db?.execSQL("ALTER TABLE room_temp RENAME TO room")
//                  db?.execSQL("delete from $tab_studentlogin")
            }
        }
    }