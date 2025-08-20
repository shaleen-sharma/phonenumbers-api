package au.com.belong.phonenumbers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "User_Phone_Number")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneNumber implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_PHONE_NUMBER_SEQ")
    @SequenceGenerator(name = "USER_PHONE_NUMBER_SEQ", sequenceName = "USER_PHONE_NUMBER_SEQ", allocationSize = 1)
    private int id;
    @Column(name = "user_id")
    private int userId;
    private long number;
    @Enumerated(EnumType.STRING)
    private PhoneNumberStatus status;
}
