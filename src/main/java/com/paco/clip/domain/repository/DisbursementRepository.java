package com.paco.clip.domain.repository;

import com.paco.clip.domain.model.Disbursement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DisbursementRepository extends CrudRepository<Disbursement, Long> {

    /**
     *
     * @param user name or id to find info
     * @return list of all disbursements made to a a client.
     */
    List<Disbursement> findAllByDestinationUser(String user);
}
