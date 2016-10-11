package org.ernest.applications.goplus.services.impl;


import org.ernest.applications.goplan.ct.CreateUserInput;
import org.ernest.applications.goplan.ct.GetUserDetailsOutput;
import org.ernest.applications.goplus.entities.MyProfileInformation;
import org.ernest.applications.goplus.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public long introduceUser(String facebookId, String username) {
        CreateUserInput createUserInput = new CreateUserInput();
        createUserInput.setFacebookId(facebookId);
        createUserInput.setName(username);

        return new RestTemplate().postForObject("http://localhost:6661/user/create", createUserInput, Long.class);
    }

    @Override
    public MyProfileInformation getMyProfileInformation(Long userId) {
        GetUserDetailsOutput output =  new RestTemplate().getForObject("http://localhost:6661/user/details/" + userId, GetUserDetailsOutput.class);

        MyProfileInformation myProfileInformation = new MyProfileInformation();
        myProfileInformation.setDescription(output.getDescription() == null ? "" : output.getDescription());
        myProfileInformation.setEat(output.getEat());
        myProfileInformation.setDrink(output.getDrink());
        myProfileInformation.setParty(output.getParty());
        myProfileInformation.setSleep(output.getSleep());
        myProfileInformation.setWork(output.getWork());
        myProfileInformation.setMature(output.getMature());
        myProfileInformation.setSport(output.getSport());
        myProfileInformation.setMovies(output.getMovies());
        myProfileInformation.setSpirituality(output.getSpirituality());
        myProfileInformation.setTravel(output.getTravel());
        myProfileInformation.setFreak(output.getFreak());
        myProfileInformation.setAnimals(output.getAnimals());

        return myProfileInformation;
    }
}


