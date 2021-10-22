package com.dailycodebuffer.department.controller;

import com.dailycodebuffer.department.entity.Department;
import com.dailycodebuffer.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping("/")
    public Department saveDepartment(@RequestBody Department department){
        log.info("save department controller");
        return service.save(department);
    }

    @GetMapping("/{id}")
    public Department findDeparmentById(@PathVariable("id") Long id){
        log.info("find department controller");
        return service.findDepartmentById(id);
    }
}
