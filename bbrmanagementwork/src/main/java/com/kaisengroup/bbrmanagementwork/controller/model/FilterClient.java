package com.kaisengroup.bbrmanagementwork.controller.model;

import lombok.*;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FilterClient {
private String name;
private String type;
private String cliente;


    public FilterClient(String name, String type, String cliente) {
        this.name = name;
        this.type = type;
        this.cliente = cliente;
    }

}
