package com.example.rentiaserver.order.model.to;
import com.example.rentiaserver.base.model.to.BaseEntityTo;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTo extends BaseEntityTo {
    private LocationTo destinationFrom;
    private LocationTo destinationTo;
    private Set<PackageTo> packages;
    private Long authorId;
    private String salary;
    private String weight;
    private String weightUnit;
    private Boolean requireTransportWithClient;
}
