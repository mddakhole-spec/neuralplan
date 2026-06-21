package com.neuralplan.neuralplan.service;

import com.neuralplan.neuralplan.model.StudyPlan;
import com.neuralplan.neuralplan.repository.StudyPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudyPlanService {
    @Autowired
    private StudyPlanRepository studyPlanRepository;

    @Autowired
    private GeminiService geminiService;

    public StudyPlan savePlan(StudyPlan studyPlan){

        //Call Gemini AI here
        String aiPlan = geminiService.generateStudyPlan(
                studyPlan.getStudentName(),
                studyPlan.getSubjects(),
                studyPlan.getExamDate(),
                studyPlan.getWeakTopics()

        );

        studyPlan.setGeneratedPlan(aiPlan);
        return studyPlanRepository.save(studyPlan);
    }
    public List<StudyPlan> getAllPlans(){
        return studyPlanRepository.findAll();
    }
}
