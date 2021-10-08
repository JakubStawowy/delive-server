package com.example.rentiaserver.data.to;
import com.example.rentiaserver.data.api.BaseEntityTo;

import java.util.Set;

public class AnnouncementTo extends BaseEntityTo {

    private final LocationTo destinationFrom;
    private final LocationTo destinationTo;
    private final Set<PackageTo> packages;
    private final Long authorId;
    private final String amount;

    public AnnouncementTo(Long id, String createdAt, LocationTo destinationFrom, LocationTo destinationTo, Set<PackageTo> packages, Long authorId, String amount) {
        super(id, createdAt);
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.packages = packages;
        this.authorId = authorId;
        this.amount = amount;
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

    public String getAmount() {
        return amount;
    }
}
