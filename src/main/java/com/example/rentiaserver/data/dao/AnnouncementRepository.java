package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.AnnouncementPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AnnouncementRepository extends JpaRepository<AnnouncementPo, Long> {}