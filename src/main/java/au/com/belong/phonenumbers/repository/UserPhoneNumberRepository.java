package au.com.belong.phonenumbers.repository;


import au.com.belong.phonenumbers.model.UserPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPhoneNumberRepository extends PagingAndSortingRepository<UserPhoneNumber, Integer>,
        JpaRepository<UserPhoneNumber, Integer> {

    Optional<List<UserPhoneNumber>> findByUserId(int userId);

    Optional<UserPhoneNumber> findByNumberAndUserId(long number, int userId);

}
