package mfs.net.br.dev.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mfs.net.br.dev.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
