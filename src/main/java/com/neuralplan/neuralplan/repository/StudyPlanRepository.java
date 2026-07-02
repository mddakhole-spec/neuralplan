package com.neuralplan.neuralplan.repository;

import com.neuralplan.neuralplan.model.StudyPlan;
import com.neuralplan.neuralplan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyPlanRepository extends JpaRepository<StudyPlan,Long> {
    List<StudyPlan> findByUserOrderByCreatedAtDesc(User user);
}
