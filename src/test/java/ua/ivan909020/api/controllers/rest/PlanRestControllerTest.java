package ua.ivan909020.api.controllers.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.ivan909020.api.domain.DurationUnit;
import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.domain.dto.PlanDto;
import ua.ivan909020.api.mappers.PlanMapper;
import ua.ivan909020.api.mappers.PlanMapperImpl;
import ua.ivan909020.api.services.PlanService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ivan909020.api.JsonMapper.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanRestController.class)
public class PlanRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private PlanService planService;

    @MockBean(classes = PlanMapperImpl.class)
    private PlanMapper planMapper;

    @Test
    @WithMockUser(value = "username")
    public void findById() throws Exception {
        Plan plan = createStubPlan(1);
        PlanDto planDto = convertToDto(plan);

        when(planService.findById(1)).thenReturn(plan);
        when(planMapper.toDto(plan)).thenReturn(planDto);

        mock.perform(
                get("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(planDto)));

        verify(planService).findById(1);
        verify(planMapper).toDto(plan);
    }

    @Test
    @WithMockUser(value = "username")
    public void findById_notFound() throws Exception {
        mock.perform(
                get("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(planService).findById(1);
        verify(planMapper, times(0)).toDto(any(Plan.class));
    }

    @Test
    @WithMockUser(value = "username")
    public void create() throws Exception {
        Plan plan = createStubPlan(null);
        PlanDto planDto = convertToDto(plan);
        Plan createdPlan = createStubPlan(1);
        PlanDto createdPlanDto = convertToDto(createdPlan);

        when(planMapper.toEntity(planDto)).thenReturn(plan);
        when(planService.create(plan)).thenReturn(createdPlan);

        mock.perform(
                post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(planDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJson(createdPlanDto)));

        verify(planMapper).toEntity(createdPlanDto);
        verify(planService).create(plan);
    }

    @Test
    @WithMockUser(value = "username")
    public void create_badRequest() throws Exception {
        Plan plan = createStubPlan(null);
        plan.setName(null); // required parameter -> bad request
        PlanDto planDto = convertToDto(plan);

        mock.perform(
                post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(planDto)))
                .andExpect(status().isBadRequest());

        verify(planMapper, times(0)).toEntity(planDto);
        verify(planService, times(0)).create(plan);
    }

    @Test
    @WithMockUser(value = "username")
    public void update() throws Exception {
        Plan plan = createStubPlan(1);
        PlanDto planDto = convertToDto(plan);

        when(planService.existsById(1)).thenReturn(true);
        when(planMapper.toEntity(planDto)).thenReturn(plan);

        mock.perform(
                put("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(planDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(toJson(planDto)));

        verify(planService).existsById(1);
        verify(planMapper).toEntity(planDto);
        verify(planService).update(plan);
    }

    @Test
    @WithMockUser(value = "username")
    public void update_notFound() throws Exception {
        Plan plan = createStubPlan(1);
        PlanDto planDto = convertToDto(plan);

        when(planService.existsById(1)).thenReturn(false);

        mock.perform(
                put("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(planDto)))
                .andExpect(status().isNotFound());

        verify(planService).existsById(1);
        verify(planMapper, times(0)).toEntity(planDto);
        verify(planService, times(0)).update(plan);
    }

    @Test
    @WithMockUser(value = "username")
    public void update_badRequest() throws Exception {
        Plan plan = createStubPlan(1);
        plan.setName(null); // required parameter -> bad request
        PlanDto planDto = convertToDto(plan);

        when(planService.existsById(1)).thenReturn(true);
        when(planMapper.toEntity(planDto)).thenReturn(plan);

        mock.perform(
                put("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(planDto)))
                .andExpect(status().isBadRequest());

        verify(planService, times(0)).existsById(1);
        verify(planMapper, times(0)).toEntity(planDto);
        verify(planService, times(0)).update(plan);
    }

    @Test
    @WithMockUser(value = "username")
    public void deleteById() throws Exception {
        when(planService.existsById(1)).thenReturn(true);

        mock.perform(
                delete("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(planService).existsById(1);
        verify(planService).deleteById(1);
    }

    @Test
    @WithMockUser(value = "username")
    public void deleteById_notFound() throws Exception {
        when(planService.existsById(1)).thenReturn(false);

        mock.perform(
                delete("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(planService).existsById(1);
        verify(planService, times(0)).deleteById(1);
    }

    @Test
    @WithMockUser("username")
    public void findAll() throws Exception {
        List<Plan> plans = Arrays.asList(createStubPlan(1), createStubPlan(2), createStubPlan(3));
        List<PlanDto> plansDto = convertToDto(plans);

        when(planMapper.toDto(plans)).thenReturn(plansDto);
        when(planService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(plans));

        mock.perform(
                get("/plans")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(plansDto)));

        verify(planMapper).toDto(plans);
        verify(planService).findAll(any(Pageable.class));
    }

    private Plan createStubPlan(Integer id) {
        Plan plan = new Plan();
        plan.setId(id);
        plan.setName("Plan");
        plan.setPrice(1.0F);
        plan.setDurationUnit(DurationUnit.DAY);
        plan.setDurationCount(1);
        return plan;
    }

    private PlanDto convertToDto(Plan plan) {
        PlanDto planDto = new PlanDto();
        planDto.setId(plan.getId());
        planDto.setName(plan.getName());
        planDto.setPrice(plan.getPrice());
        planDto.setDurationUnit(plan.getDurationUnit().name());
        planDto.setDurationCount(plan.getDurationCount());
        return planDto;
    }

    private List<PlanDto> convertToDto(List<Plan> plans) {
        List<PlanDto> result = new ArrayList<>();
        for (Plan plan : plans) {
            result.add(convertToDto(plan));
        }
        return result;
    }

}
