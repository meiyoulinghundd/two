package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserMapper;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionIdListener;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
     IUserService userService;


    /**
     *
     *  注册接口
     * */

    @RequestMapping(value = "register.do")
    public ServerResponse register(User user){

     return  userService.register(user);

    }

    /**
     *
     *  登陆接口
     * */

    @RequestMapping(value = "login.do/{username}/{password}")
    public ServerResponse  longin(@PathVariable("username") String username,
                                  @PathVariable("password") String password,
                                  HttpSession session){
        ServerResponse serverResponse=userService.login(username,password,1);
        if(serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;

    }
    /**
     *
     *  忘记密码   根据帐号获取密保问题
     * */
    @RequestMapping("forget_get_question/{username}")
    public ServerResponse forget_get_question(@PathVariable("username") String username){

  return  userService.forget_get_question(username);
    }
    /**
     *
     *  提交答案
     * */


    @RequestMapping("forget_check_answer")
    public ServerResponse forget_check_answer(String username,String question,String answer){





        return  userService.forget_check_answer(username,question,answer);
    }


     // 重设密码


    @RequestMapping("forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String newpassword,String forgettoken){





        return  userService.forget_reset_password(username,newpassword,forgettoken);
    }

    //修改部分信息
    @RequestMapping(value = "update_information")
    public ServerResponse update_information(HttpSession session,User user){

     User loginUser=(User)session.getAttribute(Const.CURRENT_USER);
     if(loginUser==null){
         return  ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
     }
                  user.setId(loginUser.getId());
       ServerResponse serverResponse=userService.update_information(user);
        return  serverResponse;
    }

//退出登陆
@RequestMapping(value = "logout")
public ServerResponse logout(HttpSession session){

 session.removeAttribute(Const.CURRENT_USER);

    return  ServerResponse.serverResponseBySuccess();
}

}
