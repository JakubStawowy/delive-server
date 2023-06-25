package com.example.rentiaserver.order.repository;

import com.example.rentiaserver.geolocation.model.po.LocationPo;
import com.example.rentiaserver.order.model.po.OrderPo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderPo> queryOrderTos(String initialAddress,
                                       String finalAddress,
                                       String minimalSalary,
                                       Boolean requireTransportWithClient,
                                       Boolean sortBySalary) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderPo> query = cb.createQuery(OrderPo.class);

        Root<OrderPo> root = query.from(OrderPo.class);
        Join<OrderPo, LocationPo> initialLocationJoin = root.join("initialLocationPo");
        Join<OrderPo, LocationPo> finalLocationJoin = root.join("finalLocationPo");


        List<Predicate> predicates = new ArrayList<>();

        // Initial address
        predicates.add(cb.like(initialLocationJoin.get("address"), initialAddress));

        // Final address
        predicates.add(cb.like(finalLocationJoin.get("address"), finalAddress));

        // Transport with client requirement
        if (requireTransportWithClient != null) {
            predicates.add(cb.equal(root.get("requireTransportWithClient"), requireTransportWithClient));
        }

        // Minimal salary
        if (minimalSalary != null && !minimalSalary.isEmpty()) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), minimalSalary));
        }

        List<Order> orders = Arrays.asList(cb.desc(root.get("createdAt")));
        if (Boolean.TRUE.equals(sortBySalary)) {
            orders.add(cb.desc(root.get("salary")));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(orders);

        TypedQuery<OrderPo> typedQuery = entityManager.createQuery(query);

        return typedQuery.getResultList();
    }
}
