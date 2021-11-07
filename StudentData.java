/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

/**
 *
 * @author taylajadepark
 */

import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class StudentData {
    private ArrayList<Student> studentList = null;

    public StudentData() {
        studentList = new ArrayList<>();
    }
    public ArrayList<Student> getStudentList() {
        return studentList;
    }
    public void setStudentList(ArrayList<Student> list) {
        studentList = list;
    }
    public ArrayList<Student> addStudent(Student student) {
        getStudentList().add(student);
        return studentList;
    }
    public void search(String search) {
        int check = 0;
        System.out.println("Search Name or Course: " +search);
        for (Student e : studentList) {
            if (e.getFirst_name().trim().equalsIgnoreCase(search.trim())) {
                System.out.println(e);  
            }
            for (int i = 0; i < e.getCourse().getCourseList().size(); i++) {
                if (e.getCourse().getCourseList().get(i).getCourseNo().trim().equalsIgnoreCase(search.trim())) {
                    System.out.println(e);
                }
            }
        }
        if (check == 0) {
            System.out.println("No Student/Course Found");
        }
    }
}