package group.aist.cinema.controller;

import group.aist.cinema.dto.common.HallDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/halls")
public class HallController {

    private final HallService hallService;

    @GetMapping
    public BaseResponse<Page<HallDTO>> getAllHalls(Pageable pageable) {
        return BaseResponse.success(hallService.getAllHalls(pageable));
    }

    @GetMapping("/{id}")
    public BaseResponse<HallDTO> getHallById(@PathVariable Long id) {
        return BaseResponse.success(hallService.getHallById(id));
    }

    @PostMapping
    public BaseResponse<HallDTO> createHall(@RequestBody HallDTO hallDTO) {
        return BaseResponse.created(hallService.createHall(hallDTO));
    }

    @PutMapping("/{id}")
    public BaseResponse<HallDTO> updateHall(@PathVariable Long id, @RequestBody HallDTO hallDTO) {
        return BaseResponse.success(hallService.updateHall(id, hallDTO));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
        return BaseResponse.noContent();
    }


}
