package com.example.rentiaserver.data.services.order;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.PackagePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public final class OrderService {

    private final OrderDao orderDao;
    private final FilteredOrderService filteredAnnouncementsDao;
    private final PackageDao packageDao;

    @Autowired
    public OrderService(OrderDao announcementRepository, FilteredOrderService filteredAnnouncementsDao, PackageDao packageRepository) {
        this.orderDao = announcementRepository;
        this.filteredAnnouncementsDao = filteredAnnouncementsDao;
        this.packageDao = packageRepository;
    }

    public void save(AnnouncementPo announcementPo) {
        orderDao.save(announcementPo);
    }

    public void deleteById(Long id) {
        orderDao.deleteById(id);
    }

    public Optional<AnnouncementPo> getAnnouncementById(Long id) {
        return orderDao.findById(id);
    }

    public List<AnnouncementPo> getAllAnnouncements() {
        return orderDao.findAll();
    }

    public void saveAllPackages(Set<PackagePo> packagePos) {
        packageDao.saveAll(packagePos);
    }

    public void deleteAllByAnnouncement(AnnouncementPo announcementPo) {
        packageDao.deleteAllByAnnouncementPo(announcementPo);
    }

    public List<AnnouncementPo> findAnnouncementsByAddresses(String initialAddress, String finalAddress, String minimalSalary,
                                                             String requireTransportWithClient, boolean sortBySalary) {
        return filteredAnnouncementsDao.findAnnouncementsByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient, sortBySalary);
    }
}