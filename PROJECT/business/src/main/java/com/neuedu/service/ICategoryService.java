package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

public interface ICategoryService {

    /**
     *
     * 添加类别
     * */


    public ServerResponse addCategory(Category category);
    /**
     *
     * 修改类别
     * categoryId
     * categoryName
     * categoryUrl
     * */

    public ServerResponse updateCategory(Category category);

    /**
     *
     * 查看平级类别
     * categoryId
     *
     * */

    public ServerResponse getCategoryById( Integer categoryId);

    /**
     *
     * 查看平级类别
     * categoryId
     *
     * */
    public ServerResponse deepCategory( Integer categoryId);
/***
 * 根据id查类别
 */
public ServerResponse<Category> selectCategory( Integer categoryId);

}
