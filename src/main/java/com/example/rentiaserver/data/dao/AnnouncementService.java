package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.DeliveryAnnouncementPo;
import com.example.rentiaserver.data.po.NormalAnnouncementPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    private final DeliveryAnnouncementRepository deliveryAnnouncementRepository;
    private final NormalAnnouncementRepository normalAnnouncementRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(DeliveryAnnouncementRepository deliveryAnnouncementRepository, NormalAnnouncementRepository normalAnnouncementRepository, AnnouncementRepository announcementRepository) {
        this.deliveryAnnouncementRepository = deliveryAnnouncementRepository;
        this.normalAnnouncementRepository = normalAnnouncementRepository;
        this.announcementRepository = announcementRepository;
    }

    public List<DeliveryAnnouncementPo> getAllDeliveryAnnouncements() {
        return deliveryAnnouncementRepository.findAll();
    }

    public List<NormalAnnouncementPo> getAllNormalAnnouncements() {
        return normalAnnouncementRepository.findAll();
    }

    public void save(DeliveryAnnouncementPo deliveryAnnouncement) {
        deliveryAnnouncementRepository.save(deliveryAnnouncement);
    }

    public void save(NormalAnnouncementPo normalAnnouncement) {
        normalAnnouncementRepository.save(normalAnnouncement);
    }

    public void deleteById(Long id) {
        announcementRepository.deleteById(id);
    }
}

@Repository
interface DeliveryAnnouncementRepository extends JpaRepository<DeliveryAnnouncementPo, Long> {}

@Repository
interface NormalAnnouncementRepository extends JpaRepository<NormalAnnouncementPo, Long> {}

@Repository
interface AnnouncementRepository extends JpaRepository<AnnouncementPo, Long> {}
