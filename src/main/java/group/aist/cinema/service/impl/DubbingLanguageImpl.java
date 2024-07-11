package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.DubbingLanguageDTO;
import group.aist.cinema.mapper.DubbingLanguageMapper;
import group.aist.cinema.model.DubbingLanguage;
import group.aist.cinema.model.MovieStream;
import group.aist.cinema.repository.DubbingLanguageRepository;
import group.aist.cinema.repository.MovieStreamRepository;
import group.aist.cinema.service.DubbingLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DubbingLanguageImpl implements DubbingLanguageService {

    private final DubbingLanguageRepository dubbingLanguageRepository;
    private final MovieStreamRepository movieStreamRepository;
    private final DubbingLanguageMapper dubbingLanguageMapper;

    @Override
    public Page<DubbingLanguageDTO> getAllDubbingLanguages(Pageable pageable) {
        Page<DubbingLanguage> dubbingLanguages = dubbingLanguageRepository.findAll(pageable);
        return dubbingLanguages.map(dubbingLanguageMapper::toDTO);
    }

    @Override
    public DubbingLanguageDTO getDubbingLanguage(Long id) {
        return dubbingLanguageMapper.toDTO(dubbingLanguageRepository.findById(id).orElseThrow(() -> new RuntimeException("Dubbing language not found with id " + id)));
    }

    @Override
    public DubbingLanguageDTO createDubbingLanguage(DubbingLanguageDTO dubbingLanguageDTO) {
        DubbingLanguage dubbingLanguage = dubbingLanguageMapper.toEntity(dubbingLanguageDTO);
        return dubbingLanguageMapper.toDTO(dubbingLanguageRepository.save(dubbingLanguage));
    }

    @Override
    public DubbingLanguageDTO updateDubbingLanguage(Long id, DubbingLanguageDTO dubbingLanguageDTO) {
        DubbingLanguage dubbingLanguage = dubbingLanguageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dubbing language not found with id " + id));
        dubbingLanguageMapper.updateDubbingLanguageFromDTO(dubbingLanguageDTO, dubbingLanguage);
        return dubbingLanguageMapper.toDTO(dubbingLanguageRepository.save(dubbingLanguage));
    }

    @Override
    public void deleteDubbingLanguage(Long id) {
        dubbingLanguageRepository.deleteById(id);
    }


}
