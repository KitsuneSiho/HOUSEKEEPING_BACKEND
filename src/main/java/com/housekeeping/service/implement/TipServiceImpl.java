package com.housekeeping.service.implement;

import com.housekeeping.entity.Tip;
import com.housekeeping.repository.TipRepository;
import com.housekeeping.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public Tip getTipById(Long id) {
        return tipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tip not found with id: " + id));
    }

    @Override
    public Tip saveTip(Tip tip) {
        if (tip.getTipTitle() == null || tip.getTipTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        }
        if (tip.getTipContent() == null || tip.getTipContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        tip.setTipCreatedDate(LocalDateTime.now());
        tip.setTipViews(0);
        tip.setComments(new ArrayList<>());

        return tipRepository.save(tip);
    }

    @Override
    public Tip updateTip(Long id, Tip tipDetails) {
        Tip tip = getTipById(id);
        tip.setTipCategory(tipDetails.getTipCategory());
        tip.setTipTitle(tipDetails.getTipTitle());
        tip.setTipContent(tipDetails.getTipContent());
        return tipRepository.save(tip);
    }

    @Override
    public void deleteTip(Long id) {
        Tip tip = getTipById(id);
        tipRepository.delete(tip);
    }

    @Override
    @Transactional
    public Tip incrementViewAndGetTip(Long id) {
        Tip tip = getTipById(id);
        tip.setTipViews(tip.getTipViews() + 1);
        return tipRepository.save(tip);
    }
}