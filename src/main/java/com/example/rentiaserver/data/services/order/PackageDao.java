package com.example.rentiaserver.data.services.order;


import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.PackagePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PackageDao extends CrudRepository<PackagePo, Long> {
    //    @Query(value = "UPDATE PACKAGES SET IS_ARCHIVED=TRUE WHERE ANNOUNCEMENT_ID=", nativeQuery = true)
    void deleteAllByAnnouncementPo(AnnouncementPo announcementPo);
}
