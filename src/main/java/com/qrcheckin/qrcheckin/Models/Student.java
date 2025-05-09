package com.qrcheckin.qrcheckin.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Student {

    @Getter
    private static String[] sorts = {"name","email", "id"};

    protected Student(){}

    public Student(String name, String email, String qr){
        this.name = name;
        this.email = email;
        this.qr = qr;
        this.groups = new HashSet<>();
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String qr;

    @ManyToMany
    @JoinTable(
       name= "student_group",
       joinColumns = @JoinColumn (name = "student_id"),
       inverseJoinColumns = @JoinColumn (name = "group_id")
    )
    private Set<Group> groups;

    public String displayGroups(){
        return Strings.join(this.groups.stream().map(Group::getName).toList(),',');
    }
}
