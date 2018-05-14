package com.bmdb.business.actor;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActorRepository extends CrudRepository<Actor,Integer> {

//	Actor findByUserNameAndPassword(String uname, String pwd);
}