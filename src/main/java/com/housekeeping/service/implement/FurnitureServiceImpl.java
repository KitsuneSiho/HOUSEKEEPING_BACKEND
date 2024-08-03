package com.housekeeping.service.implement;

import com.housekeeping.DTO.FurnitureDTO;
import com.housekeeping.entity.Furniture;
import com.housekeeping.repository.FurnitureRepository;
import com.housekeeping.service.FurnitureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FurnitureServiceImpl implements FurnitureService {

    private final FurnitureRepository furnitureRepository;

    @Override
    public List<FurnitureDTO> getFurnitureList(int level) {
        return furnitureRepository.getFurnitureList(level);
    }
}
