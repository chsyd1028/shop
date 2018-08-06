package com.csyd.shop.controller;

import com.csyd.shop.model.Goods;
import com.csyd.shop.service.GoodsService;
import com.csyd.shop.service.RedisService;
import com.csyd.shop.util.ReturnDto;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

/**
 * Created by ChengShanyunduo
 * 2018/8/6
 */
@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/goods/list")
    @ResponseBody
    public ReturnDto goodsList(@RequestParam("pageNum") int pageNum){
        PageInfo pageInfo = goodsService.goodsList(pageNum);
        return ReturnDto.buildSuccessReturnDto(pageInfo);
    }

    @RequestMapping("/")
    public String goodsListPage(){
        return "goods";
    }

    @RequestMapping("/goods/addGoods")
    @ResponseBody
    public ReturnDto addGoods(@RequestParam("goodsId") int goodsId){
        //这里的userId应该是获取到的
        int userId = 1;
        int count = goodsService.addGoods(userId, goodsId);
        return ReturnDto.buildSuccessReturnDto(count);
    }

    @RequestMapping("/goods/shoppingCartCount")
    @ResponseBody
    public ReturnDto shoppingCartCount(){
        //这里的userId应该是获取到的
        int userId = 1;
        int count = goodsService.getShoppingCartCount(userId);
        return ReturnDto.buildSuccessReturnDto(count);
    }

    @RequestMapping("/goods/shopping_cart")
    public String shoppingCartPage(){
        return "shopping_cart";
    }

    @RequestMapping("/goods/shoppingCartList")
    @ResponseBody
    public ReturnDto shoppingCartList(){
        //这里的userId应该是获取到的
        int userId = 1;
        List<Goods> list = goodsService.getShoppingCartList(userId);
        return ReturnDto.buildSuccessReturnDto(list);
    }

    @RequestMapping("/goods/shoppingCartString")
    @ResponseBody
    public ReturnDto shoppingCartString (){
        //这里的userId应该是获取到的
        int userId = 1;
        String str = redisService.getValue("user:" + userId + ":shoppingcart");
        return ReturnDto.buildSuccessReturnDto(str);
    }

    @RequestMapping("/goods/insertShoppingCartStr")
    @ResponseBody
    public ReturnDto insertShoppingCartStr(@RequestParam("shoppingCartStr") String shoppingCartStr){
        int userId = 1;
        redisService.setValue("user:" + userId + ":shoppingcart", shoppingCartStr);
        return ReturnDto.buildSuccessReturnDto();
    }
}
