package com.neuralplan.neuralplan.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;    //null for OAuth (Google) users
    private String provider;    //"local" or "google"

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StudyPlan> studyPlans;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getProvider() {return provider;}
    public void setProvider(String provider) {this.provider = provider;}

    public List<StudyPlan> getStudyPlans() {return studyPlans;}
    public void setStudyPlans(List<StudyPlan> studyPlans) {this.studyPlans = studyPlans;}
}
