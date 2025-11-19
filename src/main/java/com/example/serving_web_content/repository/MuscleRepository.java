package com.example.serving_web_content.repository;

import com.example.serving_web_content.model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface MuscleRepository extends JpaRepository<Muscle, Long> {
}
