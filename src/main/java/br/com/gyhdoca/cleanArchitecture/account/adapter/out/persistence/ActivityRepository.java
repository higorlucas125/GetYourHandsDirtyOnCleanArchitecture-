package br.com.gyhdoca.cleanArchitecture.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityJpaEntity,Long> {
    @Query("""
			select a from ActivityJpaEntity a 
			where a.ownerAccountId = :ownerAccountId 
			and a.localDateTime >= :since
			""")
    List<ActivityJpaEntity> findByOwnerSince(
            @Param("ownerAccountId") long ownerAccountId,
            @Param("since") LocalDateTime since);

    @Query("""
			select sum(a.amount) from ActivityJpaEntity a
			where a.targetAccountId = :accountId
			and a.ownerAccountId = :accountId
			and a.localDateTime < :until
			""")
    Optional<Long> getDepositBalanceUntil(
            @Param("accountId") long accountId,
            @Param("until") LocalDateTime until);

    @Query("""
			select sum(a.amount) from ActivityJpaEntity a
			where a.sourceAccountId = :accountId
			and a.ownerAccountId = :accountId
			and a.localDateTime < :until
			""")
    Optional<Long> getWithdrawalBalanceUntil(
            @Param("accountId") long accountId,
            @Param("until") LocalDateTime until);
}
