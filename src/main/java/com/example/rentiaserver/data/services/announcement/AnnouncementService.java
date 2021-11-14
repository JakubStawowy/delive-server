package com.example.rentiaserver.data.services.announcement;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.PackagePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public final class AnnouncementService {

    private final AnnouncementDao announcementDao;
    private final FilteredAnnouncementsDao filteredAnnouncementsDao;
    private final PackageDao packageDao;

    @Autowired
    public AnnouncementService(AnnouncementDao announcementRepository, FilteredAnnouncementsDao filteredAnnouncementsDao, PackageDao packageRepository) {
        this.announcementDao = announcementRepository;
        this.filteredAnnouncementsDao = filteredAnnouncementsDao;
        this.packageDao = packageRepository;
    }

    public void save(AnnouncementPo announcementPo) {
        announcementDao.save(announcementPo);
    }

    public void deleteById(Long id) {
        announcementDao.deleteById(id);
    }

    public Optional<AnnouncementPo> getAnnouncementById(Long id) {
        return announcementDao.findById(id);
    }

    public List<AnnouncementPo> getAllAnnouncements() {
        return announcementDao.findAll();
    }

    public void saveAllPackages(Set<PackagePo> packagePos) {
        packageDao.saveAll(packagePos);
    }

    public void deleteAllByAnnouncement(AnnouncementPo announcementPo) {
        packageDao.deleteAllByAnnouncementPo(announcementPo);
    }

    public List<AnnouncementPo> findAnnouncementsByAddresses(String initialAddress, String finalAddress, String minimalSalary, String requireTransportWithClient) {
        return filteredAnnouncementsDao.findAnnouncementsByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient);
    }
}