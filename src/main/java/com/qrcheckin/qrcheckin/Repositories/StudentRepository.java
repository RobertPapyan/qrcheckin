package com.qrcheckin.qrcheckin.Repositories;

import com.qrcheckin.qrcheckin.Models.Group;
import com.qrcheckin.qrcheckin.Models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByEmail(String email);

    Page<Student> findByGroups(Group group, Pageable page);

    @EntityGraph(attributePaths = {"groups"})
    @Query("""
    SELECT DISTINCT s FROM Student s
    JOIN s.groups g
    WHERE g.proffessor.id = :userId
    AND (:groupNames IS NULL OR g.name IN :groupNames)
    AND (:search IS NULL OR s.name LIKE %:search%)
    """)
    Page<Student> findStudentsByUserAndOptionalGroupNames(
            @Param("userId") Long userId,
            @Param("groupNames") List<String> groupNames,
            @Param("search") String search,
            Pageable pageable
    );

    boolean existsByEmail(String email);
}
