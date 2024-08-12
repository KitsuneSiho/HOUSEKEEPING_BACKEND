package com.housekeeping.service.implement;

import com.housekeeping.entity.Tip;
import com.housekeeping.repository.TipRepository;
import com.housekeeping.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;

    @Autowired
    public TipServiceImpl(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    @Override
    public List<Tip> getAllTips() {
       return tipRepository.findAll();
    }
}
