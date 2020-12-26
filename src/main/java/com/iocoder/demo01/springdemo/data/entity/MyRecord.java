package com.iocoder.demo01.springdemo.data.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "myrecord")
//@TableGenerator(name="tab", initialValue=1)
public class MyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
    @Column(name="idMyRecord")
    private Integer idMyRecord;
    private Date record_date;
    private Date record_time;
    private String category;
    private String details;

    public Integer getId() {
        return idMyRecord;
    }

    public void setId(Integer id) {
        this.idMyRecord = id;
    }

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        this.record_date = record_date;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "MyRecord{" +
                "idMyRecord=" + idMyRecord +
                ", record_date=" + record_date +
                ", record_time=" + record_time +
                ", category='" + category + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
