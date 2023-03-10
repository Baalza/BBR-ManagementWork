package com.kaisengroup.bbrmanagementwork.controller.repository;

import java.util.List;

import com.kaisengroup.bbrmanagementwork.controller.model.Component;
import com.kaisengroup.bbrmanagementwork.controller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
    //@Query("SELECT Component FROM component WHERE idwork = idwork")
    List<Component> findByFkworkOrderByIdAsc(int fkwork);

}
