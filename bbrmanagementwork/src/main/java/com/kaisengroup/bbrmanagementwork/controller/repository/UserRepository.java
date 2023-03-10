package com.kaisengroup.bbrmanagementwork.controller.repository;

import com.kaisengroup.bbrmanagementwork.controller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
