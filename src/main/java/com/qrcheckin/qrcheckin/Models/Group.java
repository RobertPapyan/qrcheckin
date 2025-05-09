package com.qrcheckin.qrcheckin.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(
        name = "class_group",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"key","user_id"})
        }
)
public class Group {

    @Getter
    private static final String[] sorts = {"name","id"};

    protected Group(){}

    public Group(String name, String key, User proffessor){
        this.name = name;
        this.groupKey = key;
        this.proffessor = proffessor;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String groupKey;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User proffessor;

}
