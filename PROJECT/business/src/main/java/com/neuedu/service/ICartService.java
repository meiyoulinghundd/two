package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Cart;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ICartService {
    /**
     * 添加商品购物车
     * */
    public ServerResponse addProductCart(Integer userId,Integer productId,Integer count);

    //根据用户id查询用户已选中的商品
    public  ServerResponse<List<Cart>> findCartsByUseridAndChecked(Integer userId);


    //批量删除购物车商品

    public ServerResponse deleteBatch(List<Cart> cartList);


}
