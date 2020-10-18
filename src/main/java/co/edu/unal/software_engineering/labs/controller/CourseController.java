package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.pojo.CoursePOJO;
import co.edu.unal.software_engineering.labs.service.CourseService;
import co.edu.unal.software_engineering.labs.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CourseController{

    private final CourseService courseService;
    private final RoleService roleService;

    public CourseController(RoleService roleService, CourseService courseService ){
        this.roleService = roleService;
        this.courseService = courseService;

    }

    @PostMapping( value = {"/principal/profesor/crear-curso/{roleId}"} )
    public ResponseEntity<Void>  createCourse (@PathVariable Integer roleId, @RequestBody CoursePOJO coursePOJO ){
        Role role =roleService.findById(roleId);

        if(role == null || !role.getRoleName().equals("Profesor")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Course newCourse = new Course();
        newCourse.setCourseName(coursePOJO.getCourseName().toUpperCase());
        newCourse.setDurationHours(coursePOJO.getDurationHours());
        courseService.save(newCourse);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}