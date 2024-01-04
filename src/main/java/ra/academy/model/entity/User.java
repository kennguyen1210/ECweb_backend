package ra.academy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    @Column(length = 100, unique = true)
    private String username;
    private String email;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    private Boolean status;
    private String password;
    private String avatar;
    @Column(length = 15, unique = true)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Temporal(TemporalType.DATE)
    private Date create_at;
    @Temporal(TemporalType.DATE)
    private Date update_at;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
