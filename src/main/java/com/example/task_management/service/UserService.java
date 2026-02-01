package com.example.task_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.task_management.entity.UserEntity;
import com.example.task_management.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.pagination.user.page-size}")
    private int pageSize;

    public Page<UserEntity> listAll(int pageNum, String sortField, String sortDirection) {

        Sort sort = sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        int actualPage = (pageNum < 1) ? 0 : pageNum - 1;

        Pageable pageable = PageRequest.of(actualPage, pageSize, sort);

        return userRepository.findAll(pageable);
    }
}
