package com.kaisengroup.bbrmanagementwork.controller.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table (name = "\"worksheet\"")
public class WorkSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "fkcomp")
    private int fkcomp;
    @Column(name = "orainizio")
    private String orainizio;
    @Column(name = "orafine")
    private String orafine;
    @Column(name = "type")
    private String type;

    @Column(name = "data")
    private String data;

    public WorkSheet(String name, int fkcomp, String orainizio, String orafine, String type, String data) {
        this.name = name;
        this.fkcomp = fkcomp;
        this.orainizio = orainizio;
        this.orafine = orafine;
        this.type = type;
        this.data = data;
    }
}
