package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface XferRepository extends CrudRepository<Xfer, Long> {
    Set<Xfer> findAllByAcctno(String acctno);
}
