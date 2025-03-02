package dz.tal.lead.LeadHelper.auth.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "jwt_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_JWT_TOKENS_USER")
    )
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String token;

    @Builder.Default
    @Column(nullable = false)
    private Boolean revoked = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean expired = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}