package com.kaisengroup.bbrmanagementwork.controller.model;

import lombok.*;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "\"work\"")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private @Setter String name;
    @Column(name = "data")
    private @Setter String data;
    @Column(name = "type")
    private @Setter String type;
    @Column(name = "status")
    private @Setter Boolean status;
    @Column(name = "cliente")
    private @Setter String cliente;
    @Column(name = "ordine")
    private @Setter String ordine;

    public Work(String name, String data, String type, Boolean status, String cliente, String ordine) {
        this.name = name;
        this.data = data;
        this.type = type;
        this.status = status;
        this.cliente = cliente;
        this.ordine = ordine;
    }

}
