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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

@SpringBootApplication
public class SearchFunction {

	public static ArrayList<Student> parseJSOn(String url) throws ParseException {
		StudentList studentList = new StudentList();

		Client client = Client.create();
		WebResource webResource = client.resource(url);

		ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
		if (clientResponse.getStatus() != 200) {
			throw new RuntimeException("Failed" + clientResponse.toString());
		}
		JSONArray jsonArray = (JSONArray) new JSONParser().parse(clientResponse.getEntity(String.class));

		Iterator<Object> it =  jsonArray.iterator();

		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			JSONArray courseList = (JSONArray) jsonObject.get("course");
			CourseList coursesList = new CourseList();

			if(courseList != null) {
				Iterator<Object> a = courseList.iterator();
				while (a.hasNext()) {
					JSONObject courseObject = (JSONObject) a.next();
					Course course = new Course((courseObject.get("courseNo").toString()),courseObject.get("grade").toString(), Integer.parseInt(courseObject.get("creditHours").toString()));
					coursesList.addCourse(course);
				}
			}
			Student student = new Student(Integer.parseInt(jsonObject.get("id").toString()), jsonObject.get("first_name").toString(), jsonObject.get("email").toString(), jsonObject.get("gender").toString(), coursesList);
			studentList.addStudent(student);
		}
		System.out.println(studentList.getStudentList());
		return studentList.getStudentList();
	}

	public static void main(String[] args) throws ParseException {

		SpringApplication.run(DemoApplication.class, args);
		StudentList studentList = new StudentList();
		studentList.setStudentList(parseJSOn("https://hccs-advancejava.s3.amazonaws.com/student_course.json"));
		System.out.println(studentList.getStudentList());
		studentList.search("Aida");
		studentList.search("CS");

		for (Student e : studentList.getStudentList()) {
			if(!e.getCourse().getCourseList().isEmpty()){
				System.out.println("GPA: " + e.getFirst_name()+" : "+e.calculateGPA());
			}
			else System.out.println("GPA missing "+e.getFirst_name());
		}
	}

}