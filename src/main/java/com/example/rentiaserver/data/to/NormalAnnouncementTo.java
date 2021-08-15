package com.example.rentiaserver.data.to;

public class NormalAnnouncementTo extends AnnouncementTo {

    private final String packageLength;
    private final String packageWidth;
    private final String packageHeight;

    public NormalAnnouncementTo(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, Long authorId, String packageLenght, String packageWidth, String packageHeight) {
        super(fromLatitude, fromLongitude, toLatitude, toLongitude, authorId);
        this.packageLength = packageLenght;
        this.packageWidth = packageWidth;
        this.packageHeight = packageHeight;
    }

    public String getPackageLength() {
        return packageLength;
    }

    public String getPackageWidth() {
        return packageWidth;
    }

    public String getPackageHeight() {
        return packageHeight;
    }
}
