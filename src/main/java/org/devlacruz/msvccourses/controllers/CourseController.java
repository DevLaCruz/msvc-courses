package org.devlacruz.msvccourses.controllers;

import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.devlacruz.msvccourses.entity.Course;
import org.devlacruz.msvccourses.services.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class CourseController {
    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<List<Course>> list(){
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<Course> o = service.forId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validator(result);
        }
        Course coursedb=service.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(coursedb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validator(result);
        }
        Optional<Course> o = service.forId(id);
        if (o.isPresent()) {
            Course coursedb= o.get();
            coursedb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(coursedb));
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Course> o = service.forId(id);
        if (o.isPresent()) {
            service.delete(o.get().getId());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


    //Método que valida:
    private ResponseEntity<?> validator(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
