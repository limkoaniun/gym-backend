package com.example.serving_web_content.repository;

import com.example.serving_web_content.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
