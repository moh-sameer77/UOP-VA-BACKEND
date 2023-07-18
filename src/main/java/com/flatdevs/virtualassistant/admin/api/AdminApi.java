package com.flatdevs.virtualassistant.admin.api;

import com.flatdevs.virtualassistant.contract.Result;
import com.flatdevs.virtualassistant.student.repository.AcademicPlanRepository;
import com.flatdevs.virtualassistant.student.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class AdminApi {
    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private AcademicPlanRepository academicPlanRepository;

    @GetMapping("/get/courses/academicplans")
    public ResponseEntity<Result> getAllCoursesAndAcademicPlans()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("courses", coursesRepository.findAll());
        map.put("academicPlans", academicPlanRepository.findAll());
        return ResponseEntity.ok(Result.builder().success(true).message(map).build());
    }
}