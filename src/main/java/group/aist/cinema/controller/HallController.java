package group.aist.cinema.controller;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/halls")
public class HallController {

    private final HallService hallService;

    @GetMapping
    public Page<HallDTO> getAllHalls(Pageable pageable) {
        return hallService.getAllHalls(pageable);
    }

    @GetMapping("/{id}")
    public HallDTO getHallById(@PathVariable Long id) {
        return hallService.getHallById(id);
    }

    @PostMapping
    public HallDTO createHall(@RequestBody HallDTO hallDTO) {
        return hallService.createHall(hallDTO);
    }

    @PutMapping("/{id}")
    public HallDTO updateHall(@PathVariable Long id, @RequestBody HallDTO hallDTO) {
        return hallService.updateHall(id, hallDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
    }


}
