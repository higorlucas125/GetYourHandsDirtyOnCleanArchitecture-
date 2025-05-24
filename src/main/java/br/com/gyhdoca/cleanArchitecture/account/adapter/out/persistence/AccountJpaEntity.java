package br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountJpaEntity {
    @Id
    @GeneratedValue
    private Long id;
}
