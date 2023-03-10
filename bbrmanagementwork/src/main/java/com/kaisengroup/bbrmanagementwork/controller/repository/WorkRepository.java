package com.kaisengroup.bbrmanagementwork.controller.repository;

import java.util.List;

import javax.websocket.Session;

import com.kaisengroup.bbrmanagementwork.controller.model.Work;

import org.hibernate.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {

    // @Query("select * from Work where type == true")
    List<Work> findByStatusTrue();

    List<Work> findByStatusFalse();
    List<Work> findByType(String type);
    List<Work> findByCliente(String cliente);
   
    @Query("SELECT DISTINCT cliente FROM Work w WHERE w.status = false")
    List<String> findDistinctCliente();
    
    //@Query("SELECT * FROM Work w WHERE w.type = type AND w.cliente = cliente")
    List<Work> findAllByTypeAndCliente(String type, String cliente);

}
