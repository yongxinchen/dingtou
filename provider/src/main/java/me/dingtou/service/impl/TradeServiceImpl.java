package me.dingtou.service.impl;

import me.dingtou.constant.StockType;
import me.dingtou.manager.StockManager;
import me.dingtou.manager.TradeManager;
import me.dingtou.model.Order;
import me.dingtou.model.Stock;
import me.dingtou.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private StockManager stockManager;


    @Autowired
    private TradeManager tradeManager;

    @Override
    public Order conform(String owner, StockType type, String code) {
        Stock stock = stockManager.query(owner, type, code);
        if (null == stock) {
            return null;
        }
        return tradeManager.conform(stock);
    }

    @Override
    public Order buy(Order order) {
        return tradeManager.buy(order);
    }

    @Override
    public List<Order> settlement(String owner) {
        List<Order> result = new ArrayList<>();
        // 查询所有标的
        List<Stock> stocks = stockManager.query(owner, null);
        // 找出未完成的交易
        for (Stock stock : stocks) {
            List<Order> settlement = tradeManager.settlement(stock);
            if (null == settlement) {
                continue;
            }
            result.addAll(settlement);
        }
        return result;
    }
}