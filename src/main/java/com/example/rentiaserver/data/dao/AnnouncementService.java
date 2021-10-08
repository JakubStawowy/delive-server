package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.AnnouncementPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
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
}