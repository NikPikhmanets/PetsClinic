package com.defaultvalue.petsclinic.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

//    @Query(
//            value = "SELECT " +
//                    "   r.id, " +
//                    "   r.name " +
//                    "FROM users_roles ur " +
//                    "   INNER JOIN roles r ON r.id = ur.role_id " +
//                    "WHERE ur.user_id = :userId", nativeQuery = true)
//    List<Role> findRolesByUserId(int userId);
//
//    void insertRoleForUser(Integer id);
}
