package org.devlacruz.msvccourses.services;

import java.util.List;
import java.util.Optional;

import org.devlacruz.msvccourses.entity.Course;
import org.devlacruz.msvccourses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> list() {
       return(List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> forId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return repository.save(course);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
