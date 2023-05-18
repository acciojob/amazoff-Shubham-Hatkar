package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService
{
    //@Autowired
    OrderRepository orderRepository = new OrderRepository();

    public OrderService() {

    }

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
        List<String> list = orderRepository.getOrdersByPartnerId(partnerId);
        return list;
    }

    public List<String> getAllOrders()
    {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders()
    {
        int totalOrders = orderRepository.getAllOrders().size();
        int assignedOrder = orderRepository.getAllAssignedOrder();
        return totalOrders - assignedOrder;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String t, String partnerId)
    {
        String time[] = t.split(":");
        int HH = Integer.valueOf(time[0]);
        int MM = Integer.valueOf(time[1]);
        int currTime = (HH * 60) + MM;

        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(currTime,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        String HH = String.valueOf(time / 60);
        String MM = String.valueOf(time % 60);
        if(HH.length() < 2) HH = HH + '0';
        if(MM.length() < 2) MM = MM + '0';
        return HH + ":" + MM;
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
