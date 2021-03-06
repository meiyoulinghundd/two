package com.neuedu.dao;

import com.neuedu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */


    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     *
     * 判断用户名是否存在
     *
     * */
    int isexistsusername(@Param("username") String username);

    /**
     *
     * 判断邮箱是否存在
     *
     * */
    int isexistsemail(@Param("email") String email);

    /**
     *
     * 根据用户名，密码查询
     *
     * */
    User findUserByUsernameAndPassword(@Param("username") String username,
                                       @Param("password") String password);

    /**
     * 根据username获取密保问题
     * */
    public String forget_get_question(@Param("username") String username);

    /**
     * 提交答案
     * */
 public int forget_check_answer(@Param("username") String username,
                                   @Param("question") String question,
                                   @Param("answer") String answer);

    //* *修改密码

    public int forget_reset_password(@Param("username") String username,
                                     @Param("password") String newpassword);


//修改用户信息

     public int  updateUserByActive(@Param("user") User user);
}