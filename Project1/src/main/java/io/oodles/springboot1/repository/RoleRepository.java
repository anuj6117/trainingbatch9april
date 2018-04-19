package io.oodles.springboot1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.oodles.springboot1.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	//Role findOne(Integer roleid);

}
