package com.housekeeping.entity;


import com.housekeeping.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"friendUserId1", "friendUserId2"})
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "friendUserId1", nullable = false)
    private User friendUser1;

    @ManyToOne
    @JoinColumn(name = "friendUserId2", nullable = false)
    private User friendUser2;

    @Column(nullable = false)
    private LocalDateTime friendDate = LocalDateTime.now();
}
