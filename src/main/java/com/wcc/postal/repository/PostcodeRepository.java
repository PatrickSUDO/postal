package com.wcc.postal.repository;

import com.wcc.postal.model.Postcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostcodeRepository extends JpaRepository<Postcode, Long> {


}
