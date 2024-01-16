package org.devlacruz.msvccourses.services;

import java.util.List;
import java.util.Optional;

import org.devlacruz.msvccourses.entity.Course;

public interface CourseService {
    List<Course> list();
    Optional<Course> forId(Long id);
    Course save(Course course);
    void delete(Long id);
}
