package com.neuralplan.neuralplan.service;

import com.neuralplan.neuralplan.model.User;
import com.neuralplan.neuralplan.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request){
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        userRepository.findByEmail(email).orElseGet(()->{
            User newuser = new User();
            newuser.setEmail(email);
            newuser.setName(name);
            newuser.setProvider("google");
            return userRepository.save(newuser);
        });
        return oAuth2User;
    }
}
