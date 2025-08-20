package au.com.belong.phonenumbers.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Phone_User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneUser implements Serializable {
    @Id
    @Column(name = "user_id")
    private int userId;
    private String fullname;
}
