package com.example.rentiaserver.data.specifications;

import com.example.rentiaserver.data.api.AnnouncementKeysRepository;
import com.example.rentiaserver.data.api.OperationsRepository;
import com.example.rentiaserver.data.api.SpecificationTemplate;
import com.example.rentiaserver.data.po.AnnouncementPo;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public final class AnnouncementSpecification extends SpecificationTemplate<AnnouncementPo> {

    public AnnouncementSpecification(String key, String operation, String value) {
        super(key, operation, value);
    }

    @Override
    public Predicate toPredicate(@NotNull Root<AnnouncementPo> root, @NotNull CriteriaQuery<?> criteriaQuery, @NotNull CriteriaBuilder criteriaBuilder) {

        Predicate predicate;
        if ((predicate = super.toPredicate(root, criteriaQuery, criteriaBuilder)) != null)
            return predicate;

        if(operation.equalsIgnoreCase(OperationsRepository.EQUALS))
            switch (key) {
                case AnnouncementKeysRepository.TIME_UNIT:
                    predicate = criteriaBuilder.equal(root.get(key), TimeUnit.of(ChronoUnit.valueOf(value)));
                    break;
                case AnnouncementKeysRepository.TITLE:
                    predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(key)),
                            "%" + value.toLowerCase() + "%"
                    );
                    break;
                default:
                    predicate = criteriaBuilder.equal(root.get(key), value);
                    break;
            }


        else if(operation.equalsIgnoreCase(OperationsRepository.NOT_EQUAL))
            switch (key) {
                case AnnouncementKeysRepository.TIME_UNIT:
                    predicate = criteriaBuilder.notEqual(root.get(key), TimeUnit.of(ChronoUnit.valueOf(value)));
                    break;
                case AnnouncementKeysRepository.TITLE:
                    predicate = criteriaBuilder.notEqual(criteriaBuilder.lower(root.get(key)), value.toLowerCase());
                    break;
                default:
                    predicate = criteriaBuilder.notEqual(root.get(key), value);
                    break;
            }

        return predicate;
    }

    @Override
    protected void validate(final String key, final String operation, final String value) {

    }
}