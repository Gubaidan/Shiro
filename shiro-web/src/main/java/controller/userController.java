package controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import po.User;


@Controller
public class userController {

    @RequestMapping(value = "/subLogin" , method = RequestMethod.POST,
    produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassward());
        try {
            subject.login(usernamePasswordToken);
        }catch (AuthenticationException e){
            return e.getMessage();
        }



        return "登陆成功";
    }
}
