package com.dailycodebuffer.cloud.gateway;

import com.dailycodebuffer.cloud.gateway.vo.Department;
import com.dailycodebuffer.cloud.gateway.vo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/userServiceFallBack")
    public User userServiceFallBackMethod(){
        var user = new User();
        user.setFirstName("User service is taking longer than expected."+
                "Please try again later");
        return user;
    }

    @GetMapping("/departmentServiceFallBack")
    public Department departmentServiceFallBackMethod(){
        var department = new Department();
        department.setAddress("User department is taking longer than expected."+
                "Please try again later");
        return department;

    }
}
