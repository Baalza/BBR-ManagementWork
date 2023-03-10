package com.kaisengroup.bbrmanagementwork.controller.model;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table (name = "\"component\"")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "data")
    private String data;
    @Column(name = "fkwork")
    private int fkwork;
    @Column(name = "prezzo")
    private String prezzo;
    @Column(name = "materiale")
    private String materiale;
    @Column(name = "fornitore")
    private String fornitore;
    @Column(name = "peso")
    private String peso;
    @Column(name = "costruiti")
    private int costruiti;
    @Column(name = "consegnati")
    private int consegnati;
    @Column(name = "magazzino")
    private int magazzino;
    @Column(name = "lavesterne")
    private String lavesterne;
    @Column(name = "totore")
    private float totore;
    @Column(name = "annotazioni")
    private String annotazioni;

    public Component(String name, String data, int fkwork, String prezzo, String materiale, String fornitore, String peso, int costruiti, int consegnati, int magazzino, String lavesterne, float totore, String annotazioni ) {
        this.name = name;
        this.data = data;
        this.fkwork = fkwork;
        this.prezzo = prezzo;
        this.materiale = materiale;
        this.fornitore = fornitore;
        this.peso = peso;
        this.costruiti = costruiti;
        this.consegnati = consegnati;
        this.magazzino = magazzino;
        this.lavesterne = lavesterne;
        this.totore = totore;
        this.annotazioni = annotazioni;
    }
}
