package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService
{
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order)
    {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId)
    {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId)
    {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId)
    {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId)
    {
        List<Order> list = orderRepository.getOrdersByPartnerId(partnerId);
        List<String> ans = new ArrayList<>();
        for(Order o : list)
        {
            ans.add(o.getId());
        }
        return ans;
    }

    public List<String> getAllOrders()
    {
        List<Order> list = orderRepository.getAllOrders();
        List<String> ans = new ArrayList<>();
        for(Order o : list)
        {
            ans.add(o.getId());
        }
        return ans;
    }

    public Integer getCountOfUnassignedOrders()
    {
        int totalOrders = orderRepository.getAllOrders().size();
        int assignedOrder = orderRepository.getAllAssignedOrder();
        return totalOrders - assignedOrder;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId)
    {
        int deliveryTime = (Integer.valueOf(time.substring(0,3)) * 60) +
                Integer.valueOf(time.substring(4));

        List<Order> list = orderRepository.getOrdersByPartnerId(partnerId);

        int undelivered = 0;
        for(Order order : list)
        {
            int currdeliveryTime = order.getDeliveryTime();
            if(currdeliveryTime > deliveryTime) undelivered++;
        }
        return undelivered;
    }

    public void deletePartnerById(String partnerId)
    {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId)
    {
        orderRepository.deleteOrderById(orderId);
    }
}
