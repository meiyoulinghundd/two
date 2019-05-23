package com.neuedu.service.impl;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserMapper;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse register(User user) {

     //step1:参数校验
     if(user==null){
        return ServerResponse.serverResponseByError(ResponseCode.PARAM_NOT_NULL,"参数不能为空");
     }
     //step2: 判断用户名是否存在
     int result=userMapper.isexistsusername(user.getUsername());
     if(result>0){//用户名已存在
         return ServerResponse.serverResponseByError(ResponseCode.USERNAME_EXISTS,"用户名已存在");
     }
     //step3:判断邮箱是否存在
        int resultemail=userMapper.isexistsemail(user.getEmail());
        if(resultemail>0){//邮箱已存在
            return ServerResponse.serverResponseByError(ResponseCode.EMAIL_EXISTS,"邮箱已存在");
        }
     //step4: MD5密码加密，设置用户角色 ADMIN ---XXXXXXXXXXXXX
       user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        //设置角色为普通用户
        user.setRole(RoleEnum.ROLE_USER.getRole());
     //step5: 注册
       int insertResult= userMapper.insert(user);
       if(insertResult<=0){
           return ServerResponse.serverResponseByError(ResponseCode.ERROR,"注册失败");
       }
     //step6：返回
      return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse login(String username, String password,int type) {

        /*
        参数校验
        *
         *  **/
        if(username==null||username.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"用户名不能为空" );
        }
        if(password==null||password.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"密码不能为空" );
        }
       /*
        判断用户名存在
        *
         *  **/
   int result=  userMapper.isexistsusername(username);
     if(result<=0){
         return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"用户名不存在");
     }
     //密码加密
            password=MD5Utils.getMD5Code(password);
     //denglu
       User user=userMapper.findUserByUsernameAndPassword(username,password);
       if(user==null){
           return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"密码错误");
       }

       if (type==0){
           if(user.getRole()==RoleEnum.ROLE_USER.getRole()){
               return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"没有权限");
           }
       }

       return  ServerResponse.serverResponseBySuccess(user);
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        //1参数非空校验
        if(username==null||username.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"用户名不能为空" );
        }
        //根据用户名查询问题
        String question=userMapper.forget_get_question(username);
        if(question==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"没有查询到问题");
        }
        //返回结果
        return ServerResponse.serverResponseBySuccess(question);
    }

    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //1参数非空校验
        if(username==null||username.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"用户名不能为空" );
        }
        if(question==null||question.equals(" ")){
             return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"密保问题不能为空" );
        }
        if(answer==null||answer.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"密保答案不能为空" );
        }
        //校验答案
        int result =userMapper.forget_check_answer(username,question,answer );
        if(result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"答案不对");
        }
        //返回结果
//生成TOKEN
        String token=UUID.randomUUID().toString();
        TokenCache.set("username:"+username,token);

        return ServerResponse.serverResponseBySuccess(token);
    }

    @Override
    public ServerResponse forget_reset_password(String username, String newpassword, String forgettoken) {
        //1参数非空校验
        if(username==null||username.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"用户名不能为空" );
        }
        if(newpassword==null||newpassword.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"xin密码不能为空" );
        }
        if(forgettoken==null||forgettoken.equals(" ")){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"token不能为空" );
        }

         String  token=  TokenCache.get("username:"+username);
              if(token==null){
                  return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"不能修改别人密码或者时间过期");
              }
              if (!token.equals(forgettoken)){
                  return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"无效TOKEN");
              }





       int result= userMapper.forget_reset_password(username,newpassword);

        if(result<=0){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ," 密码修改失败");
        }
        return ServerResponse.serverResponseBySuccess();
    }

    @Override
    public ServerResponse update_information(User user) {

//1参数非空校验
        if(user==null){
            return ServerResponse.serverResponseByError(ResponseCode.ERROR ,"参数不能为空" );
        }

      int result=userMapper.updateUserByActive(user);
        if(result<=0){
            return  ServerResponse.serverResponseByError(ResponseCode.ERROR,"修改失败");
        }
        return ServerResponse.serverResponseBySuccess();
    }

}
