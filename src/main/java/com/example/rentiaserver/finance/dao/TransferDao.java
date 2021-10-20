package com.example.rentiaserver.finance.dao;

import com.example.rentiaserver.finance.po.TransferPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDao extends CrudRepository<TransferPo, Long> {
}
