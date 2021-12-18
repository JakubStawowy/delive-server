package com.example.rentiaserver.data.services.order;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.po.AnnouncementPo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OrderDao extends JpaRepository<AnnouncementPo, Long> {
    @Query(value = "SELECT * FROM ANNOUNCEMENTS WHERE " + ApplicationConstants.Sql.NOT_ARCHIVED + " " + ApplicationConstants.Sql.ORDER_BY_CREATED_AT, nativeQuery = true)
    @NotNull
    List<AnnouncementPo> findAll();
}