package io.book.member.domain;

import io.book.common.domain.At;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(
        indexes = {
                @Index(name = "idx_member_1", columnList = "key")
        }
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String key;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Embedded
    private At at;

    public static Member of(final String key, final String password) {
        Member member = new Member();
        member.key = key;
        member.password = password;
        member.at = At.create();

        return member;
    }

    public void edit(final String password) {
        this.password = password;
        this.at.update();
    }

    public boolean isValidPassword(final PasswordEncoder passwordEncoder, final String password) {
        return passwordEncoder.matches(password, this.password);
    }
}