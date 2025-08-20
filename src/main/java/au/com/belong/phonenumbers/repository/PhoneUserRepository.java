package au.com.belong.phonenumbers.repository;


import au.com.belong.phonenumbers.model.PhoneUser;
import au.com.belong.phonenumbers.model.UserPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneUserRepository extends JpaRepository<PhoneUser, Integer> {

}
