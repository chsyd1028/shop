package com.csyd.shop.service;

import com.csyd.shop.mapper.GoodsMapper;
import com.csyd.shop.model.Goods;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by ChengShanyunduo
 * 2018/8/6
 */
@Service
public class GoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisService redisService;

    public PageInfo goodsList(int pageNum){
        PageHelper.startPage(pageNum, 12);
        List<Goods> list = goodsMapper.selectAll();
        PageInfo page = new PageInfo(list);
        return page;
    }

    public int addGoods(int userId, int goodsId){
        String idsStr = redisService.getValue("user:" + userId + ":shoppingcart");
        if (Objects.isNull(idsStr) || "".equals(idsStr)){
            idsStr = String.valueOf(goodsId);
        }else {
            idsStr = idsStr + "," + goodsId;
        }
        redisService.setValue("user:" + userId + ":shoppingcart", idsStr);

        String[] ids = idsStr.split(",");
        return ids.length;
    }

    public int getShoppingCartCount(int userId){
        List<Integer> goodsIds = new ArrayList<>();
        String goodsStr = redisService.getValue("user:" + userId + ":shoppingcart");
        if (Objects.isNull(goodsStr) || "".equals(goodsStr)){
            return 0;
        }else {
            String ids = redisService.getValue("user:" + userId + ":shoppingcart");
            int[] idsNum = Arrays.stream(ids.split(",")).mapToInt(s -> Integer.parseInt(s)).toArray();
            goodsIds = Arrays.stream( idsNum ).boxed().collect(Collectors.toList());
            return goodsIds.size();
        }
    }

    public List<Goods> getShoppingCartList(int userId){
        List<Goods> goodsList = new ArrayList<>();
        String goodsStr = redisService.getValue("user:" + userId + ":shoppingcart");
        if (Objects.nonNull(goodsStr)){
            String goodsIds[] = goodsStr.split(",");
            goodsList = goodsMapper.selectInShoppingCart(goodsIds);
        }
        return goodsList;

    }
}
