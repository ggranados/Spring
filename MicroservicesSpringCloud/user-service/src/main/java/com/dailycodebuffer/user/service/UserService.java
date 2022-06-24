package com.dailycodebuffer.user.service;

import com.dailycodebuffer.user.entity.User;
import com.dailycodebuffer.user.repository.UserRepository;
import com.dailycodebuffer.user.vo.Department;
import com.dailycodebuffer.user.vo.ResponseTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public User save(User user) {
        log.info("save user service");
        return repository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long id) {
        log.info("find user with department service");

        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = repository.findById(id).get();
        Department department = restTemplate
                .getForObject("http://API-GATEWAY/departments/" + user.getDepartmentId(), Department.class);

        vo.setUser(user);
        vo.setDepartment(department);
        return vo;
    }
}
