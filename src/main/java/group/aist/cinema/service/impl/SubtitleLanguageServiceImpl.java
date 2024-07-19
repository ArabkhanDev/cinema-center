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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SubtitleLanguageServiceImpl implements SubtitleLanguageService {
    private static final String SUBTITLE_NOT_FOUND = "Subtitle language not found with id ";
    private final SubtitleLanguageRepository subtitleLanguageRepository;
    private final SubtitleLanguageMapper subtitleLanguageMapper;

    @Override
    @PreAuthorize("hasRole('USER')")
    public Page<SubtitleLanguageDTO> getAllSubtitleLanguages(Pageable pageable) {
        Page<SubtitleLanguage> subtitleLanguages = subtitleLanguageRepository.findAll(pageable);
        return subtitleLanguages.map(subtitleLanguageMapper::mapToDto);
    }

    @Override
    public SubtitleLanguageDTO getSubtitleLanguageById(Long id) {
        SubtitleLanguage subtitleLanguage = subtitleLanguageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,SUBTITLE_NOT_FOUND + id));
        return subtitleLanguageMapper.mapToDto(subtitleLanguage);
    }


    @Override
    public SubtitleLanguageDTO createSubtitleLanguage(SubtitleLanguageDTO subtitleLanguageDto) {
        return subtitleLanguageMapper.mapToDto(subtitleLanguageRepository.save(subtitleLanguageMapper.mapToEntity(subtitleLanguageDto)));
    }

    @Override
    public SubtitleLanguageDTO updateSubtitleLanguage(Long id, SubtitleLanguageDTO subtitleLanguageDto) {
        SubtitleLanguage existingSubtitleLanguage = subtitleLanguageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,SUBTITLE_NOT_FOUND + id));
        return subtitleLanguageMapper.mapToDto(subtitleLanguageRepository.save(existingSubtitleLanguage));
    }

    @Override
    public void deleteSubtitleLanguage(Long id) {
        if (!subtitleLanguageRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND,SUBTITLE_NOT_FOUND + id);
        }
        subtitleLanguageRepository.deleteById(id);
    }
}