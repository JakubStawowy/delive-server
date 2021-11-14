package com.example.rentiaserver.data.api;

import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ISpecificationListProvider<T> {

    String REGEX = ";";
    List<Specification<T>> getSpecificationListWithDecompressedCriteria(Set<String[]> criteria);

    default List<Specification<T>> getSpecificationListWithCompressedCriteria(String criteria) {
        Set<String[]> result = new HashSet<>();
        String[] criteriaArray = criteria.split(REGEX);
        for (String spec: criteriaArray) {
            int i;
            for (i = 0; i < OperationsRepository.OPERATIONS.length; i++)
                if (spec.contains(OperationsRepository.OPERATIONS[i])) break;

            final String[] specArray = spec.split(OperationsRepository.OPERATIONS[i]);
            if (specArray.length == 1) result.add(new String[]{specArray[0], OperationsRepository.OPERATIONS[i], ""});
            else result.add(new String[]{specArray[0], OperationsRepository.OPERATIONS[i], specArray[1]});
        }

        return getSpecificationListWithDecompressedCriteria(result);
    }
}
