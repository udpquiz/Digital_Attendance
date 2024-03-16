import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.digital_attendance.R

// Import statements remain the same

class StudentAdapter(private val students: List<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private val selectedStudents = mutableListOf<Student>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.studentName)
        val studentRollNumber: TextView = itemView.findViewById(R.id.studentRollNumber)
        val attendanceCheckbox: CheckBox = itemView.findViewById(R.id.attendanceCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.studentName.text = student.name
        holder.studentRollNumber.text = student.rollNumber
        // Set the state of the checkbox without triggering listener
        holder.attendanceCheckbox.setOnCheckedChangeListener(null)
        holder.attendanceCheckbox.isChecked = student.isPresent
        // Implement logic to handle checkbox changes
        holder.attendanceCheckbox.setOnCheckedChangeListener { _, isChecked ->
            student.isPresent = isChecked
            if (isChecked) {
                selectedStudents.add(student)
            } else {
                selectedStudents.remove(student)
            }
        }
    }
    override fun getItemCount(): Int {
        return students.size
    }
    fun getSelectedStudents(): List<Student> {
        return selectedStudents.toList()
    }
}
