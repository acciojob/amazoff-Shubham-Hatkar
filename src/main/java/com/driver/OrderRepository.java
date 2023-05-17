package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository
{
    private Map<String, Order> orderDB; // orderID , Order obj
    private Map<String, DeliveryPartner> DeliveryPartnerDB; // partnerID, partner obj

    private Map<String, String> orderPartnerDB; // orderID , partnerID
    private Map<String, List<Order>> partnerAndOrderDB; // partnerID, list of assingned ordersIds to him

    public OrderRepository() {
        this.orderDB = new HashMap<>();
        this.DeliveryPartnerDB = new HashMap<>();
        this.orderPartnerDB = new HashMap<>();
        this.partnerAndOrderDB = new HashMap<>();
    }

    public void addOrder(Order order)
    {
        orderDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId)
    {
        DeliveryPartnerDB.put(partnerId,new DeliveryPartner(partnerId,0));
    }

    public void addOrderPartnerPair(String orderId, String partnerId)
    {
        if(partnerAndOrderDB.containsKey(DeliveryPartnerDB.get(partnerId)))
        {
            List<Order> oldList = partnerAndOrderDB.get(DeliveryPartnerDB.get(partnerId));
            oldList.add(orderDB.get(orderId));
            partnerAndOrderDB.put(partnerId,oldList);
        }
        else
        {
            List<Order> newList = new ArrayList<>();
            newList.add(orderDB.get(orderId));
            partnerAndOrderDB.put(partnerId,newList);
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

    public List<Order> getOrdersByPartnerId(String partnerId)
    {
        return partnerAndOrderDB.get(partnerId);
    }

    public List<Order> getAllOrders()
    {
        List<Order> list = new ArrayList<>();
        for(String id : orderDB.keySet())
        {
            list.add(orderDB.get(id));
        }
        return list;
    }

    public int getAllAssignedOrder()
    {
        int count = 0;
        for(String partnerId : partnerAndOrderDB.keySet())
        {
            count += partnerAndOrderDB.get(partnerId).size();
        }
        return count;
    }

    public void deletePartnerById(String partnerId)
    {
        if(partnerAndOrderDB.containsKey(partnerId))
        {
            partnerAndOrderDB.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId)
    {
        if(orderDB.containsKey(orderId)) orderDB.remove(orderId);

        for(String id : partnerAndOrderDB.keySet())
        {
            List<Order> list = partnerAndOrderDB.get(id);
            for(Order o : list)
            {
                if(o.getId().equals(orderId)) list.remove(o);
            }
        }
    }
}
