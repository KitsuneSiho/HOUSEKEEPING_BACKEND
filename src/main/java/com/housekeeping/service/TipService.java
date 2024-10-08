package com.housekeeping.service;

import com.housekeeping.entity.Tip;

import java.util.List;

public interface TipService {
    List<Tip> getAllTips();

    Tip getTipById(Long id);

    Tip saveTip(Tip tip);

    Tip updateTip(Long id, Tip tip);

    void deleteTip(Long id);

    Tip incrementViewAndGetTip(Long id);

    boolean isAuthorizedToModifyTip(Long userId, Long tipId);
}