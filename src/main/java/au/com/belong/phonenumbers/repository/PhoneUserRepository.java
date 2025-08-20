package au.com.belong.phonenumbers.repository;


import au.com.belong.phonenumbers.model.PhoneUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneUserRepository extends JpaRepository<PhoneUser, Integer> {

}
