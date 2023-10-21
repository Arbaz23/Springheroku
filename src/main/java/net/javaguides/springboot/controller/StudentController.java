package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Student;
import net.javaguides.springboot.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	// get all student
	@GetMapping("/student")
	public List<Student> getAllstudent(){
		return studentRepository.findAll();
	}		
	
	// create student rest api
	@PostMapping("/student")
	public Student createstudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	// get student by id rest api
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getstudentById(@PathVariable Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student not exist with id :" + id));
		return ResponseEntity.ok(student);
	}
	
	// update student rest api
	
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updatestudent(@PathVariable Long id, @RequestBody Student studentDetails){
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student not exist with id :" + id));
		
		student.setFirstName(studentDetails.getFirstName());
		student.setLastName(studentDetails.getLastName());
		student.setDob(studentDetails.getDob());
		student.setEmailId(studentDetails.getEmailId());
		
		Student updatedstudent = studentRepository.save(student);
		return ResponseEntity.ok(updatedstudent);
	}
	
	// delete student rest api
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Map<String, Boolean>> deletestudent(@PathVariable Long id){
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("student not exist with id :" + id));
		
		studentRepository.delete(student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
