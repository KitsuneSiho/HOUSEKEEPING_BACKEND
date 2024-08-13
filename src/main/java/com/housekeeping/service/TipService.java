package com.housekeeping.service;

import com.housekeeping.entity.Tip;

import java.util.List;

public interface TipService {
    List<Tip> getAllTips();

    Tip getTipById(Long id);

    Tip updateTip(Long id, Tip tip);

    void deleteTip(Long id);
}