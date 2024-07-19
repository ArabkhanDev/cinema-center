package group.aist.cinema.controller;

import group.aist.cinema.dto.common.BalanceDTO;
import group.aist.cinema.model.base.BaseResponse;
import group.aist.cinema.service.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService balanceService;

    private BalanceDTO balanceDTO;
    private Page<BalanceDTO> balanceDTOPage;

    @BeforeEach
    void setUp() {
        balanceDTO = new BalanceDTO();
        balanceDTO.setId(1L);

        balanceDTOPage = new PageImpl<>(Collections.singletonList(balanceDTO));
    }

    @Test
    void getAllBalances() throws Exception {
        when(balanceService.getAllBalances(any(Pageable.class))).thenReturn(balanceDTOPage);

        mockMvc.perform(get("/v1/api/balances")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(balanceDTO.getId()));
    }

    @Test
    void getBalanceById() throws Exception {
        when(balanceService.getBalanceById(anyLong())).thenReturn(balanceDTO);

        mockMvc.perform(get("/v1/api/balances/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(balanceDTO.getId()));
    }

    @Test
    void createBalance() throws Exception {
        when(balanceService.createBalance(any(BalanceDTO.class))).thenReturn(balanceDTO);

        mockMvc.perform(post("/v1/api/balances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(balanceDTO.getId()));
    }

    @Test
    void updateBalance() throws Exception {
        when(balanceService.updateBalance(anyLong(), any(BalanceDTO.class))).thenReturn(balanceDTO);

        mockMvc.perform(put("/v1/api/balances/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(balanceDTO.getId()));
    }

    @Test
    void deleteBalance() throws Exception {
        mockMvc.perform(delete("/v1/api/balances/delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
