package com.management.studentattendancesystem.base.db.model;

import jakarta.persistence.*;

@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long id;

    @JoinColumn(name = "batch_name")
    private String batchName;

    public Batch() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", batchName='" + batchName + '\'' +
                '}';
    }
}
