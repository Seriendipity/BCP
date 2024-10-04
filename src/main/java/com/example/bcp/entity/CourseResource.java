package com.example.bcp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CourseResource")
public class CourseResource {
    @Id
    @Column(name = "CourseResourceNo" , length = 10)
    private String CourseResourceNo;

    @Column(name = "Cid" , length = 10)
    private String Cid;

    @Column(name = "ResourceType" , length = 10)
    private String ResourceType;

    @Column(name = "CourseResourcePath" , length = 100 )
    private String CourseResourcePath;

    public String getCourseResourceNo() {
        return CourseResourceNo;
    }

    public void setCourseResourceNo(String courseResourceNo) {
        CourseResourceNo = courseResourceNo;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getResourceType() {
        return ResourceType;
    }

    public void setResourceType(String resourceType) {
        ResourceType = resourceType;
    }

    public String getCourseResourcePath() {
        return CourseResourcePath;
    }

    public void setCourseResourcePath(String courseResourcePath) {
        CourseResourcePath = courseResourcePath;
    }
}
