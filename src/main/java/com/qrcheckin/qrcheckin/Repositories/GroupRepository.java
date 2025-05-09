package com.qrcheckin.qrcheckin.Repositories;

import com.qrcheckin.qrcheckin.Models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group,Long> {

    @Override
    Optional<Group> findById(Long aLong);

    Optional<Group> findByGroupKeyAndProffessor_Id(String key, Long proffessorId);

    @Query("""
        SELECT g FROM Group g
        WHERE g.proffessor.id = :proffessorId
        AND (:search IS NULL OR g.name LIKE %:search%)
        """)
    Page<Group> findGroupByProffessorIdAndSearch(Long proffessorId, String search,  Pageable pageable);

    boolean existsByGroupKeyAndProffessor_Id(String key, Long proffessorId);

}
