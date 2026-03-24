package mapper;

import entity.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问层实现类：用HashMap模拟数据库，实现StudentMapper接口的方法
 * 职责：仅处理数据存储和读取，不做任何业务校验（校验交给Service层）
 */
public class StudentMapperImpl implements StudentMapper {

    // 用HashMap存储学生数据：key=学号（唯一），value=学生对象
    private final Map<String, Student> studentMap = new HashMap<>();

    @Override
    public boolean add(Student student) {
        // 若学号已存在，新增失败（返回false）；否则存入map（返回true）
        String studentId = student.getStudentId();
        if (studentMap.containsKey(studentId)) {
            return false;
        }
        studentMap.put(studentId, student);
        return true;
    }
    public Student getById(String studentId) {
        return studentMap.get(studentId);
    }
    public List<Student> getAll() {
        return new ArrayList<>(studentMap.values());
    }
    public boolean update(Student student) {
        String studentId = student.getStudentId();
        if (!studentMap.containsKey(studentId)) {
            return false;
        }
        studentMap.put(studentId, student);
        return true;
    }
    public boolean delete(String studentId) {
        if (!studentMap.containsKey(studentId)) {
            return false;
        }
        studentMap.remove(studentId);
        return true;
    }


}