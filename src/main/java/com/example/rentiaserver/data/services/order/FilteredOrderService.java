package com.example.rentiaserver.data.services.order;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.geolocation.po.LocationPo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
class FilteredOrderService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderPo> findOrdersByAddresses(String initialAddress, String finalAddress, String minimalSalary,
                                               String requireTransportWithClient, boolean sortBySalary) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderPo> query = cb.createQuery(OrderPo.class);
        Root<OrderPo> root = query.from(OrderPo.class);
        Join<OrderPo, LocationPo> initialLocationJoin = root.join("initialLocationPo");
        Join<OrderPo, LocationPo> finalLocationJoin = root.join("finalLocationPo");

        ParameterExpression<String> initialAddressExpression = cb.parameter(String.class);
        ParameterExpression<String> finalAddressExpression = cb.parameter(String.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(initialLocationJoin.get("address"), initialAddressExpression));
        predicates.add(cb.like(finalLocationJoin.get("address"), finalAddressExpression));
        if (Boolean.TRUE.toString().equals(requireTransportWithClient.toLowerCase()) || Boolean.FALSE.toString().equals(requireTransportWithClient.toLowerCase())) {
            predicates.add(cb.equal(root.get("requireTransportWithClient"), Boolean.valueOf(requireTransportWithClient)));
        }

        if (minimalSalary != null && !minimalSalary.isEmpty()) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), minimalSalary));
        }

        Order order;
        if (sortBySalary) {
            order = cb.desc(root.get("salary"));
        }
        else {
            order = cb.desc(root.get("createdAt"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(order);

        TypedQuery<OrderPo> q = entityManager.createQuery(query);
        q.setParameter(initialAddressExpression, "%" + initialAddress + "%");
        q.setParameter(finalAddressExpression, "%" + finalAddress + "%");
        return q.getResultList();
    }
}
