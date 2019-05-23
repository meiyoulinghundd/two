package com.neuedu.controller.front;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.ICartService;
import com.neuedu.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("cart")
public class CartController {

@Autowired
    ICartService cartService;
    /**
     * 添加商品的购物车
     * */
    @RequestMapping("add/{productId}/{count}")
    public ServerResponse addCart(@PathVariable ("productId") Integer productId,
                                  @PathVariable ("count") Integer count,
                                  HttpSession session){

        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.serverResponseByError(ResponseCode.NOT_LOGIN,"未登录");
        }



        return  cartService.addProductCart(user.getId(),productId,count);
    }
}
