package com.housekeeping.repository.implement;

import com.housekeeping.repository.custom.TipRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TipRepositoryImpl implements TipRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public TipRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
