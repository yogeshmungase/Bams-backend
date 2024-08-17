package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    Batch findByBatchNameAndEnabled(String batchName,boolean status);

    Batch findTop1ByBatchNameAndEnabled(String batchName, boolean status);

    List<Batch> findAllByEnabled(boolean status);
}
