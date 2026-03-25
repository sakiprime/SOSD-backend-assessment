package service;


import entity.Student;
import mapper.StudentMapper;

import java.util.List;

/**
 * 业务逻辑层实现类：实现具体业务逻辑，依赖Mapper层进行数据操作
 * 职责：处理数据校验、业务规则，调用Mapper层方法完成数据操作
 */
public class StudentServiceImpl implements StudentService {

    // 依赖Mapper层（通过构造方法注入，解耦且便于测试）
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public String addStudent(Student student) {
        // 1. 校验必填字段
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            return "新增失败：学号不能为空！";
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return "新增失败：姓名不能为空！";
        }

        // 2. 校验性别合法性
        if (!"男".equals(student.getGender()) && !"女".equals(student.getGender())) {
            return "新增失败：性别必须为'男'或'女'！";
        }

        // 3. 校验年龄范围
        if (student.getAge() < 18 || student.getAge() > 25) {
            return "新增失败：年龄必须在18-25岁之间！";
        }

        // 4. 调用Mapper层新增，返回结果
        boolean isSuccess = studentMapper.add(student);
        return isSuccess ? "新增成功！" : "新增失败：学号已存在！";
    }

    @Override
    public String getStudentByStudentId(String studentId) {
    Student student = studentMapper.getById(studentId);
    if (student == null) {
        return "未找到学号对应的学生！";
    }

    return student.toString();


    }

    @Override
    public String getStudentsByClassName(String className) {
        // 业务校验
        if (className == null || className.trim().isEmpty()) {
            return "查询失败：班级名称不能为空！";
        }
        List<Student> allStudent = studentMapper.getAll();
        StringBuilder result = new StringBuilder();
        for (Student student : allStudent) {
        if (student.getClassName().equals(className)) {
            result.append(student.toString());
            result.append("\n");
        }
        }
        if (result.isEmpty()) { //没找到任何该班学生时
            return "未找到[" + className + "]的学生！";
        }


        return  result.toString();
    }

    @Override
    public String getAllStudents() {
    List<Student> allStudent = studentMapper.getAll();
    StringBuilder result = new StringBuilder();
    for (Student student : allStudent) {
        result.append(student.toString());
        result.append("\n");
    }
    return result.toString();




    }

    @Override
    public String updateStudent(Student student) {
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            return "修改失败：学号不能为空！";
        }

        Student oldStudent = studentMapper.getById(student.getStudentId());
        if (oldStudent == null) {
            return "修改失败：原学生不存在！";
        }
        // 复用业务检验代码

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            student.setName(oldStudent.getName());
        }

        if (!"男".equals(student.getGender()) && !"女".equals(student.getGender()) && student.getGender()!=null) {
            return "修改失败：性别必须为'男'或'女'！";
        }
        if (student.getGender()==null){
            student.setGender(oldStudent.getGender());
        }
        if (student.getAge() < 18 || student.getAge() > 25) {
            return "修改失败：年龄必须在18-25岁之间！";
        }
        if (student.getClassName() == null || student.getClassName().trim().isEmpty()) {
            student.setClassName(oldStudent.getClassName());
        }
        if (student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            student.setMajor(oldStudent.getMajor());
        }

        boolean isSuccess = studentMapper.update(student);
        return isSuccess ? "修改成功！" : "修改失败：学生不存在！";


    }

    @Override
    public String deleteStudent(String studentId) {
        // 复用业务检验代码
        if (studentId == null || studentId.trim().isEmpty()) {
            return "删除失败：学号不能为空！";
        }
    boolean isSuccess = studentMapper.delete(studentId);
    return isSuccess ? "删除成功！" : "删除失败：学号不存在！";



    }
}