/**
 * Copyright 2023 FPT. All rights reserved.
 */

package fpt.com.vn.Spring_Boot_API.repository;

import fpt.com.vn.Spring_Boot_API.entity.EmailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Email repository
 */
@Repository
public interface EmailRepository extends CrudRepository<EmailEntity, Integer> {

}
