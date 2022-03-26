package com.user.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.Model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {

}
