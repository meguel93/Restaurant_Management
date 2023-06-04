package com.inn.restuarant.serviceImpl;

import com.inn.restuarant.constants.RestaurantConstants;
import com.inn.restuarant.dao.UserDao;
import com.inn.restuarant.model.User;
import com.inn.restuarant.service.UserService;
import com.inn.restuarant.utils.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));

                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return RestaurantUtil.getResponseEntity("Successfully registered", HttpStatus.OK);
                } else return RestaurantUtil.getResponseEntity("User already exists", HttpStatus.BAD_REQUEST);
            } else return RestaurantUtil.getResponseEntity(RestaurantConstants.INVALID_DATA, HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestaurantUtil.getResponseEntity(RestaurantConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false"/*requestMap.get("status")*/);
        user.setRole("User"/*requestMap.get("role")*/);

        return user;
    }
}
