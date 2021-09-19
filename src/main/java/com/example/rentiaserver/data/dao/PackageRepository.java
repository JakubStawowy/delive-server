package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.PackagePo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends CrudRepository<PackagePo, Long> {
}
