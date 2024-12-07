package com.personal.gateway.service;

import com.personal.gateway.service.contract.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


//
//    @Override
//    public String findProfileImage(String identity) {
//        try {
//            String profileImage = imageClient.findProfileImage(identity);
//            log.info(String.format(RETRIEVE_PROFILE_IMAGE_FROM_IMAGE_SERVICE_TEMPLATE, identity));
//            return profileImage;
//        } catch (FeignException feignException) {
//            log.error(feignException.getMessage());
//            throw new ResourceAccessException(IMAGE_SERVICE_RESOURCE_NOT_AVAILABLE_OR_SERVICE_IS_DOWN);
//        }
//
//    }
}
