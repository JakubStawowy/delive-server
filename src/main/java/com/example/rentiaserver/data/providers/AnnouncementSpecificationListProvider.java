package com.example.rentiaserver.data.providers;
import com.example.rentiaserver.data.api.ISpecificationListProvider;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.specifications.AnnouncementSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public final class AnnouncementSpecificationListProvider implements ISpecificationListProvider<AnnouncementPo> {
    @Override
    public List<Specification<AnnouncementPo>> getSpecificationListWithDecompressedCriteria(final Set<String[]> criteria) {
        List<Specification<AnnouncementPo>> result = new LinkedList<>();
        for (String[] specArray: criteria)
            result.add(new AnnouncementSpecification(specArray[0], specArray[1], specArray[2]));

        return result;
    }
}