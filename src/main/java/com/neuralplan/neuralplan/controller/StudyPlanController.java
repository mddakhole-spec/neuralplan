package com.neuralplan.neuralplan.controller;

import com.neuralplan.neuralplan.model.StudyPlan;
import com.neuralplan.neuralplan.model.User;
import com.neuralplan.neuralplan.repository.UserRepository;
import com.neuralplan.neuralplan.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class StudyPlanController {
    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(Principal principal) {
        String email;
        String name = "User";
        if (principal instanceof OAuth2AuthenticationToken oauthToken) {
            email = oauthToken.getPrincipal().getAttribute("email");
            String oauthName = oauthToken.getPrincipal().getAttribute("name");
            if (oauthName != null) name = oauthName;
        } else {
            email = principal.getName();
        }

        final String finalName = name;
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(finalName);
            newUser.setProvider("google");
            return userRepository.save(newUser);
        });
    }

    //Show Home page
    @GetMapping("/")
    public String index(Model model, Principal principal) {
        User user = getCurrentUser(principal);
        model.addAttribute("studyPlan", new StudyPlan());
        model.addAttribute("allPlans", studyPlanService.getPlansForUser(user));
        model.addAttribute("userName", user.getName());
        return "index";
    }

    // Handle form submission
    @PostMapping("/generate")
    public String generatePlan(@ModelAttribute StudyPlan studyPlan, Model model, Principal principal) {
        User user = getCurrentUser(principal);
        studyPlan.setUser(user);
        studyPlanService.savePlan(studyPlan);

        model.addAttribute("studyPlan", new StudyPlan());
        model.addAttribute("allPlans", studyPlanService.getPlansForUser(user));
        model.addAttribute("userName", user.getName());
        return "index";
    }
}