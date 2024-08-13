package com.housekeeping.repository.custom;

import java.util.List;

public interface RoutineRepositoryCustom {
    List<String> findDistinctRoutineGroupNamesByUserId(Long userId);
}
