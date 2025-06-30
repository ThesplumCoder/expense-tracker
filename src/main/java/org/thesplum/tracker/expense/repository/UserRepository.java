package org.thesplum.tracker.expense.repository;

import org.thesplum.tracker.expense.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
   
}