package org.ernest.applications.goplus.services;


import org.ernest.applications.goplus.entities.MyProfileInformation;

public interface UserService {
    long introduceUser(String facebookId, String username);

    MyProfileInformation getMyProfileInformation(Long u);
}
