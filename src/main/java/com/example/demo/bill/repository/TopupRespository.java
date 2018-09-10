package com.example.demo.bill.repository;

import com.example.demo.bill.model.Topup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TopupRespository extends CrudRepository<Topup, Integer > {

    Topup findByAmount(Double amount);

    @Transactional
    @Query(value = "SELECT *  FROM topups t where t.amount=?1", nativeQuery = true)
    Optional<Topup> getAmount(Double amount);
}
