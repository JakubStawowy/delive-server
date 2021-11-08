package com.example.rentiaserver.data.services;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.PackagePo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final PackageRepository packageRepository;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository, PackageRepository packageRepository) {
        this.announcementRepository = announcementRepository;
        this.packageRepository = packageRepository;
    }

    public void save(AnnouncementPo announcementPo) {
        announcementRepository.save(announcementPo);
    }

    public void deleteById(Long id) {
        announcementRepository.deleteById(id);
    }

    public Optional<AnnouncementPo> getAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }

    public List<AnnouncementPo> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public void saveAllPackages(Set<PackagePo> packagePos) {
        packageRepository.saveAll(packagePos);
    }

    public void deleteAllByAnnouncement(AnnouncementPo announcementPo) {
        packageRepository.deleteAllByAnnouncementPo(announcementPo);
    }
}

@Repository
interface AnnouncementRepository extends JpaRepository<AnnouncementPo, Long> {
    @Query(value = "SELECT * FROM ANNOUNCEMENTS WHERE " + ApplicationConstants.Sql.NOT_ARCHIVED + " " + ApplicationConstants.Sql.ORDER_BY_CREATED_AT, nativeQuery = true)
    @NotNull
    List<AnnouncementPo> findAll();
}

@Repository
interface PackageRepository extends CrudRepository<PackagePo, Long> {
//    @Query(value = "UPDATE PACKAGES SET IS_ARCHIVED=TRUE WHERE ANNOUNCEMENT_ID=", nativeQuery = true)
    void deleteAllByAnnouncementPo(AnnouncementPo announcementPo);
}

