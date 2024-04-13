package org.albert.websocketsonetoonechat.repositories;

import org.albert.websocketsonetoonechat.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>
{
    @Query("db.user.find({})")
    List<User> findAllUsers();
}
