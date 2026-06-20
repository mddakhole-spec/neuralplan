package com.neuralplan.neuralplan.controller;

import com.neuralplan.neuralplan.model.StudyPlan;
import com.neuralplan.neuralplan.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudyPlanController {
    @Autowired
    private StudyPlanService studyPlanService;

    //Show Home page
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("studyPlan",new StudyPlan());
        model.addAttribute("allPlans",studyPlanService.getAllPlans());
        return "index"; //looks for template/index.html
    }

    // Handle from submission
    @PostMapping("/generate")
    public String generatePlan(@ModelAttribute StudyPlan studyPlan, Model model) {
        //AI integrations
        studyPlan.setGeneratedPlan("AI plan will appear here soon...");
        studyPlanService.savePlan(studyPlan);
        model.addAttribute("studyPlan",new StudyPlan());
        model.addAttribute("allPlans",studyPlanService.getAllPlans());
        return "index";
    }

}
