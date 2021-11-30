package com.example.rentiaserver.data.services.announcement;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.maps.po.LocationPo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
class FilteredAnnouncementsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AnnouncementPo> findAnnouncementsByAddresses(String initialAddress, String finalAddress, String minimalSalary, String requireTransportWithClient) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AnnouncementPo> query = cb.createQuery(AnnouncementPo.class);
        Root<AnnouncementPo> root = query.from(AnnouncementPo.class);
        Join<AnnouncementPo, LocationPo> initialLocationJoin = root.join("initialLocationPo");
        Join<AnnouncementPo, LocationPo> finalLocationJoin = root.join("finalLocationPo");

        ParameterExpression<String> initialAddressExpression = cb.parameter(String.class);
        ParameterExpression<String> finalAddressExpression = cb.parameter(String.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(initialLocationJoin.get("address"), initialAddressExpression));
        predicates.add(cb.like(finalLocationJoin.get("address"), finalAddressExpression));
        if (Boolean.TRUE.toString().equals(requireTransportWithClient.toLowerCase()) || Boolean.FALSE.toString().equals(requireTransportWithClient.toLowerCase())) {
            predicates.add(cb.equal(root.get("requireTransportWithClient"), Boolean.valueOf(requireTransportWithClient)));
        }

        if (minimalSalary != null && !minimalSalary.isEmpty()) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("amount"), minimalSalary));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(cb.desc(root.get("createdAt")));

        TypedQuery<AnnouncementPo> q = entityManager.createQuery(query);
        q.setParameter(initialAddressExpression, "%" + initialAddress + "%");
        q.setParameter(finalAddressExpression, "%" + finalAddress + "%");
        return q.getResultList();
    }
}
