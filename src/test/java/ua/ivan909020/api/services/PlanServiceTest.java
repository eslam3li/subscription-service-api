package ua.ivan909020.api.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ua.ivan909020.api.domain.DurationUnit;
import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.exceptions.ValidationException;
import ua.ivan909020.api.services.impl.PlanServiceImpl;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanServiceTest {

    @MockBean(classes = PlanServiceImpl.class)
    private PlanService planService;

    @Test
    public void existsById() {
        Integer planId = 1;

        when(planService.existsById(planId)).thenReturn(true);

        boolean isPlanExists = planService.existsById(planId);

        assertTrue(isPlanExists);
    }

    @Test
    public void existsById_idIsNull_throwsException() {
        Integer planId = null;

        when(planService.existsById(planId)).thenCallRealMethod();

        try {
            planService.existsById(planId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Plan ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void findById() {
        Plan plan = createStubPlan(1);

        when(planService.findById(plan.getId())).thenReturn(plan);

        Plan foundPlan = planService.findById(1);

        assertEquals(foundPlan, plan);
    }

    @Test
    public void findById_idIsNull_throwsException() {
        Integer planId = null;

        when(planService.findById(planId)).thenCallRealMethod();

        try {
            planService.findById(planId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Plan ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void create() {
        Plan planToCreate = createStubPlan(null);

        when(planService.create(planToCreate)).thenAnswer(invocation -> {
            Plan plan = cloneStubPlan((Plan) invocation.getArguments()[0]);
            plan.setId(1);
            return plan;
        });

        Plan createdPlan = planService.create(planToCreate);

        Plan expectedPlan = createStubPlan(createdPlan.getId());

        assertNotNull(createdPlan.getId());
        assertEquals(expectedPlan, createdPlan);
    }

    @Test
    public void create_null_throwsException() {
        Plan planToCreate = null;

        when(planService.create(planToCreate)).thenCallRealMethod();

        try {
            planService.create(planToCreate);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Plan must not be NULL", e.getMessage());
        }
    }

    @Test
    public void create_withId_throwsException() {
        Plan planToCreate = createStubPlan(1);

        when(planService.create(planToCreate)).thenCallRealMethod();

        try {
            planService.create(planToCreate);
            fail();
        } catch (ValidationException e) {
            assertEquals("Plan ID must be NULL", e.getMessage());
        }
    }

    @Test
    public void update() {
        Plan createdPlan = createStubPlan(1);

        when(planService.update(createdPlan)).thenAnswer(invocation -> {
            Plan plan = cloneStubPlan((Plan) invocation.getArguments()[0]);
            plan.setName("Updated Plan");
            return plan;
        });

        createdPlan.setName("Updated Plan");
        Plan updatedPlan = planService.update(createdPlan);

        Plan expectedPlan = createStubPlan(createdPlan.getId());
        expectedPlan.setName(createdPlan.getName());

        assertEquals(expectedPlan, updatedPlan);
    }

    @Test
    public void update_null_throwsException() {
        Plan planToUpdate = null;

        when(planService.update(planToUpdate)).thenCallRealMethod();

        try {
            planService.update(planToUpdate);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Plan must not be NULL", e.getMessage());
        }
    }

    @Test
    public void update_withoutId_throwsException() {
        Plan planToUpdate = createStubPlan(null);

        when(planService.update(planToUpdate)).thenCallRealMethod();

        try {
            planService.update(planToUpdate);
            fail();
        } catch (ValidationException e) {
            assertEquals("Plan ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void deleteById() {
        Integer planId = 1;

        when(planService.findById(planId)).thenReturn(null);

        planService.deleteById(planId);
        Plan receivedPlan = planService.findById(planId);

        assertNull(receivedPlan);
    }

    @Test
    public void deleteById_idIsNull_throwsException() {
        Integer planId = null;

        doCallRealMethod().when(planService).deleteById(planId);

        try {
            planService.deleteById(planId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Plan ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void findAll() {
        Page<Plan> plans = new PageImpl<>(Arrays.asList(createStubPlan(1), createStubPlan(2), createStubPlan(3)));

        when(planService.findAll(any(Pageable.class))).thenReturn(plans);

        Page<Plan> receivedPlans = planService.findAll(Pageable.unpaged());

        assertTrue(receivedPlans.toList().containsAll(plans.toList()));
    }

    @Test
    public void findAll_pageableIsNull_throwsException() {
        Pageable pageable = null;

        when(planService.findAll(pageable)).thenCallRealMethod();

        try {
            planService.findAll(pageable);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Pageable must not be NULL", e.getMessage());
        }
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

    private Plan cloneStubPlan(Plan plan) {
        Plan clonedPlan = new Plan();
        clonedPlan.setId(plan.getId());
        clonedPlan.setName(plan.getName());
        clonedPlan.setPrice(plan.getPrice());
        clonedPlan.setDurationUnit(plan.getDurationUnit());
        clonedPlan.setDurationCount(plan.getDurationCount());
        return plan;
    }

}
