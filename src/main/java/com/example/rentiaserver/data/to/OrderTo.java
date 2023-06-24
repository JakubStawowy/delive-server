package com.example.rentiaserver.data.to;
import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.geolocation.to.LocationTo;

import java.util.Set;

public class OrderTo extends BaseEntityTo {

    private final LocationTo destinationFrom;
    private final LocationTo destinationTo;
    private final Set<PackageTo> packages;
    private final Long authorId;
    private final String salary;
    private final String weight;
    private final String weightUnit;
    private final boolean requireTransportWithClient;

    public OrderTo(Long id, String createdAt, LocationTo destinationFrom, LocationTo destinationTo, Set<PackageTo> packages,
                   Long authorId, String salary, boolean requireTransportWithClient, String weight, String weightUnit) {
        super(id, createdAt);
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.packages = packages;
        this.authorId = authorId;
        this.salary = salary;
        this.requireTransportWithClient = requireTransportWithClient;
        this.weight = weight;
        this.weightUnit = weightUnit;
    }

    public LocationTo getDestinationFrom() {
        return destinationFrom;
    }

    public LocationTo getDestinationTo() {
        return destinationTo;
    }

    public Set<PackageTo> getPackages() {
        return packages;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getSalary() {
        return salary;
    }

    public boolean isRequireTransportWithClient() {
        return requireTransportWithClient;
    }

    public String getWeight() {
        return weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }
}
