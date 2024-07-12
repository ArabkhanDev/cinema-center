package group.aist.cinema.service.impl;

import group.aist.cinema.dto.common.SubtitleLanguageDTO;
import group.aist.cinema.mapper.SubtitleLanguageMapper;
import group.aist.cinema.model.MovieStream;
import group.aist.cinema.model.SubtitleLanguage;
import group.aist.cinema.repository.MovieStreamRepository;
import group.aist.cinema.repository.SubtitleLanguageRepository;
import group.aist.cinema.service.SubtitleLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubtitleLanguageServiceImpl implements SubtitleLanguageService {

    private final SubtitleLanguageRepository subtitleLanguageRepository;
    private final SubtitleLanguageMapper subtitleLanguageMapper;

    @Override
    public Page<SubtitleLanguageDTO> getAllSubtitleLanguages(Pageable pageable) {
        Page<SubtitleLanguage> subtitleLanguages = subtitleLanguageRepository.findAll(pageable);
        return subtitleLanguages.map(subtitleLanguageMapper::mapToDto);
    }

    @Override
    public SubtitleLanguageDTO getSubtitleLanguageById(Long id) {
        SubtitleLanguage subtitleLanguage = subtitleLanguageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subtitle language not found with id: " + id));
        return subtitleLanguageMapper.mapToDto(subtitleLanguage);
    }


    @Override
    public SubtitleLanguageDTO createSubtitleLanguage(SubtitleLanguageDTO subtitleLanguageDto) {
        SubtitleLanguage subtitleLanguage = subtitleLanguageMapper.mapToEntity(subtitleLanguageDto);
        return subtitleLanguageMapper.mapToDto(subtitleLanguageRepository.save(subtitleLanguage));
    }

    @Override
    public SubtitleLanguageDTO updateSubtitleLanguage(Long id, SubtitleLanguageDTO subtitleLanguageDto) {
        SubtitleLanguage existingSubtitleLanguage = subtitleLanguageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subtitle language not found with id: " + id));
        return subtitleLanguageMapper.mapToDto(subtitleLanguageRepository.save(existingSubtitleLanguage));
    }

    @Override
    public void deleteSubtitleLanguage(Long id) {
        if (!subtitleLanguageRepository.existsById(id)) {
            throw new RuntimeException("Subtitle language not found with id: " + id);
        }
        subtitleLanguageRepository.deleteById(id);
    }
}