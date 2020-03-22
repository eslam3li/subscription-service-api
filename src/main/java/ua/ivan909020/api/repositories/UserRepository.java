package ua.ivan909020.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.ivan909020.api.domain.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Page<User> findAll(Pageable pageable);

}
