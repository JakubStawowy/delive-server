package com.example.rentiaserver.data.to;
import com.example.rentiaserver.data.api.BaseEntityTo;

import java.io.Serializable;
import java.util.Set;

public class AnnouncementTo extends BaseEntityTo {

    private final DestinationTo destinationFrom;
    private final DestinationTo destinationTo;
    private final Set<PackageTo> packages;
    private final Long authorId;
    private final String date;
    private final String amount;

    public AnnouncementTo(Long id, String createdAt, DestinationTo destinationFrom, DestinationTo destinationTo, Set<PackageTo> packages, Long authorId, String date, String amount) {
        super(id, createdAt);
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.packages = packages;
        this.authorId = authorId;
        this.date = date;
        this.amount = amount;
    }

    public DestinationTo getDestinationFrom() {
        return destinationFrom;
    }

    public DestinationTo getDestinationTo() {
        return destinationTo;
    }

    public Set<PackageTo> getPackages() {
        return packages;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }
}
