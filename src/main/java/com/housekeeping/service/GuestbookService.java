package com.housekeeping.service;

import com.housekeeping.DTO.GuestbookDTO;
import com.housekeeping.entity.Guestbook;
import com.housekeeping.entity.UserEntity;
import com.housekeeping.mapper.GuestbookMapper;
import com.housekeeping.repository.GuestbookRepository;
import com.housekeeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;
    private final UserRepository userRepository;

    public List<GuestbookDTO> findByGuestbookOwner(UserEntity guestbookOwner) {
        return guestbookRepository.findByGuestbookOwner(guestbookOwner)
                .stream()
                .map(GuestbookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GuestbookDTO save(GuestbookDTO guestDTO) {
        // Find or create User objects for the owner and writer
        UserEntity guestbookOwner = userRepository.findById(guestDTO.getGuestbookOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity guestbookWriter = userRepository.findById(guestDTO.getGuestbookWriterId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert DTO to Entity
        Guestbook guestbook = GuestbookMapper.toEntity(guestDTO, guestbookOwner, guestbookWriter);
        // Save entity and convert back to DTO
        return GuestbookMapper.toDTO(guestbookRepository.save(guestbook));
    }


    public void deleteById(Long id) {
        guestbookRepository.deleteById(id);
    }

    public void archiveById(Long id) {
        Optional<Guestbook> optionalGuestbook = guestbookRepository.findById(id);
        if (optionalGuestbook.isPresent()) {
            Guestbook guestbook = optionalGuestbook.get();
            guestbook.setGuestbookIsArchived(true);
            guestbookRepository.save(guestbook);
        } else {
            throw new RuntimeException("Guestbook entry not found");
        }
    }

    // 방 주인의 보관된 방명록 조회
    public List<GuestbookDTO> findArchivedGuestbooksByOwner(UserEntity guestbookOwner) {
        return guestbookRepository.findByGuestbookOwnerAndGuestbookIsArchived(guestbookOwner, true)
                .stream()
                .map(GuestbookMapper::toDTO)
                .collect(Collectors.toList());
    }
}
