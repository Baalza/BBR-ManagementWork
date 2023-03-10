package com.kaisengroup.bbrmanagementwork.controller.repository;

import java.util.List;

import com.kaisengroup.bbrmanagementwork.controller.model.User;
import com.kaisengroup.bbrmanagementwork.controller.model.WorkSheet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSheetRepository extends JpaRepository<WorkSheet, Integer> {
    List<WorkSheet> findByFkcompOrderByIdAsc(int fkwork);

}
