package com.sample.application.demo.repository;




import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sample.application.demo.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);


}
