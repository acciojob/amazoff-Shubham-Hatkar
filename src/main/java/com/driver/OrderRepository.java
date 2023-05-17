package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository
{
    Map<String, Order> orderDB = new HashMap<>(); // orderID , Order obj
    Map<String, DeliveryPartner> DeliveryPartnerDB = new HashMap<>(); // partnerID, partner obj

    Map<String, String> orderPartnerDB = new HashMap<>(); // orderID , partnerID
    Map<String, List<String>> partnerAndOrderDB = new HashMap<>(); // partnerID, list of assingned ordersIds to him

    public OrderRepository(){};
//    public OrderRepository() {
//        this.orderDB = new HashMap<>();
//        this.DeliveryPartnerDB = new HashMap<>();
//        this.orderPartnerDB = new HashMap<>();
//        this.partnerAndOrderDB = new HashMap<>();
//    }

    public void addOrder(Order order)
    {
        orderDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId)
    {
        DeliveryPartnerDB.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        // see if orderID and partnerID is present in DB
        if(orderDB.containsKey(orderId) && DeliveryPartnerDB.containsKey(partnerId))
        {
            // put order in orderDB
            orderPartnerDB.put(orderId, partnerId);

            List<String> list = new ArrayList<>();
            if(partnerAndOrderDB.containsKey(partnerId))
            {
                list = partnerAndOrderDB.get(partnerId);
            }
            list.add(orderId);
            partnerAndOrderDB.put(partnerId,list);

            // Increase the no of orders of partner :
            DeliveryPartner deliveryPartner = DeliveryPartnerDB.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders() + 1);
            // Or you can write
            // deliveryPartner.setNumberOfOrders(list.size()); //size of List of orders is no. of orders
        }
    }

    public Order getOrderById(String orderId)
    {
        if(orderDB.containsKey(orderId)) return orderDB.get(orderId);
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId)
    {
        if(DeliveryPartnerDB.containsKey(partnerId)) return DeliveryPartnerDB.get(partnerId);
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId)
    {
        if(!partnerAndOrderDB.containsKey(partnerId)) return 0;
        return partnerAndOrderDB.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId)
    {
        return partnerAndOrderDB.get(partnerId);
    }

    public List<String> getAllOrders()
    {
        List<String> list = new ArrayList<>();
        for(String id : orderDB.keySet())
        {
            list.add(id);
        }
        return list;
    }

    public int getAllAssignedOrder()
    {
        return orderDB.size() - orderPartnerDB.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int deliveryTime, String partnerId)
    {
        int count = 0;
        List<String> orders = partnerAndOrderDB.get(partnerId);

        for(String orderID : orders)
        {
            if(orderDB.get(orderID).getDeliveryTime() > deliveryTime) count++;
        }
        return count;
    }

    public void deletePartnerById(String partnerId)
    {
        // delete partners
        DeliveryPartnerDB.remove(partnerId);

        // get the list of orderIDs for deleting it from orderPartnerDB
        // and delete it from partnerAndOrderDB
        List<String> listOfOrders = partnerAndOrderDB.get(partnerId);
        partnerAndOrderDB.remove(partnerId);

        // delete it from orderPartnerDB
        for(String order : listOfOrders)
        {
            orderPartnerDB.remove(order);
        }
    }

    public void deleteOrderById(String orderId)
    {
        orderDB.remove(orderId);

        String partnerID = orderPartnerDB.get(orderId);
        orderPartnerDB.remove(partnerID);

        List<String> list = partnerAndOrderDB.get(partnerID);
        list.remove(orderId);

        DeliveryPartnerDB.get(partnerID).setNumberOfOrders(partnerAndOrderDB.get(partnerID).size());
    }


    public int getLastDeliveryTimeByPartnerId(String partnerId)
    {
        int maxTime = 0;
        List<String> orders = partnerAndOrderDB.get(partnerId);
        for(String orderID : orders)
        {
            int currTime = orderDB.get(orderID).getDeliveryTime();
            maxTime = Math.max(maxTime, currTime);
        }
        return maxTime;
    }
}
