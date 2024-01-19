package com.api.TaveShot.domain.recommend.repository;

import com.api.TaveShot.domain.recommend.domain.TierCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TierCountRepository extends JpaRepository<TierCount, Long> {

    @Query("select t.count from TierCount t where t.tier=:tier1")
    Integer findByTier(Integer tier1);


}
