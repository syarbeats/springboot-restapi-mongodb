package com.mitrais.cdc.mongodbapp.controller;

import com.mitrais.cdc.mongodbapp.model.User;
import com.mitrais.cdc.mongodbapp.payload.APIResponse;
import com.mitrais.cdc.mongodbapp.service.UserService;
import com.mitrais.cdc.mongodbapp.utility.EmailUtility;
import com.mitrais.cdc.mongodbapp.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    EmailUtility emailUtility;

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity UserRegister(@RequestBody User user){
        APIResponse response = userService.UserRegistration(user);

        if(response.isSuccess()){
            String bytesEncoded = new String(Base64.encodeBase64(user.getUsername().getBytes()));
            String contents = "Please klik the following link to activate your account, <br/> <a href = \"http://localhost:8080/activate?id=" +bytesEncoded+"\">Activate Account</a>";

            try {
                log.info("username--:"+ user.getUsername());
                log.info("role--:"+ user.getRole());
                log.info("token--:"+ bytesEncoded);
                emailUtility.sendEmail(user.getEmail(), bytesEncoded, user.getUsername(), contents, "[OneStopClick-Admin] Please Activate Your Account !!");
                return ResponseEntity.ok(new Utility("Check your email to activate your account", user).getResponseData());

            }catch(Exception e) {
                log.error(e.getMessage(), e);

            }
        }
        return ResponseEntity.ok(new Utility("Sent Email was failed when User Registration", response).getResponseData());
    }

   /* @RequestMapping(value="/register", method = RequestMethod.POST)
    public ResponseEntity UserRegister(@RequestBody User user){
        return ResponseEntity.ok(new Utility("User Registration", userService.UserRegistration(user)).getResponseData());
    }*/

    @RequestMapping(value="/update/user", method = RequestMethod.PATCH)
    public ResponseEntity UpdateUserData(@RequestBody User user){
        return ResponseEntity.ok(new Utility("Update User Data", userService.UpdateUserData(user)).getResponseData());
    }

    @RequestMapping(value="/delete/user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity DeleteUserData(@PathVariable("username") String username){
        return ResponseEntity.ok(new Utility("Delete User Data", userService.DeleteUserByUsername(username)).getResponseData());
    }

    @RequestMapping(value="/find/user/{id}", method = RequestMethod.GET)
    public ResponseEntity DeleteUserData(@PathVariable("id") ObjectId id){
        return ResponseEntity.ok(new Utility("Find User Data", userService.FindUserById(id)).getResponseData());
    }

    @RequestMapping(value="/find-user-by-username/{username}", method = RequestMethod.GET)
    public ResponseEntity FindUserByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok(new Utility("Find User Data By Username", userService.FindUserByUsername(username)).getResponseData());
    }

    @RequestMapping(value="/all-users", method = RequestMethod.GET)
    public ResponseEntity GetAllUsers(){
        return ResponseEntity.ok(new Utility("Find User Data", userService.GetAllUsers()).getResponseData());
    }

    @RequestMapping(value="/resetpassword", method = RequestMethod.GET)
    public ResponseEntity ResetPassword(HttpServletRequest request){

        String email = request.getParameter("email");
        User user = userService.FindUserByEmail(email);

        if(user == null){
            return ResponseEntity.ok(new Utility("User data not found", null).getResponseData());
        }

        String encodedUsername = new String(DatatypeConverter.parseBase64Binary(user.getUsername()));

        try {
            emailUtility.sendEmail(email, encodedUsername, user.getUsername(), "", "");
            return ResponseEntity.ok(new Utility("Check your email to reset your password", user).getResponseData());

        }catch(Exception e) {
            log.error(e.getMessage(), e);

        }
        return ResponseEntity.ok(new Utility("Sending email to reset password was failed", user).getResponseData());

    }

    @RequestMapping(value="/activate", method = RequestMethod.GET)
    public ResponseEntity ActivateUser(@RequestParam("id") String id){

        //byte[] usernameDecoded = Base64.decodeBase64(id.getToken().getBytes());
        byte[] usernameDecoded = Base64.decodeBase64(id.getBytes());
        String username = new String(usernameDecoded);
        log.info("USERNAME", username);
        APIResponse response = userService.ActivateUser(username);

        if(response.isSuccess())
        {
            return ResponseEntity.ok(new Utility("Your account has been activated", username).getResponseData());
        }

        return ResponseEntity.ok(new Utility("User activated was failed", null).getResponseData());
    }
}
