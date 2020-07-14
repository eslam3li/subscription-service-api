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
import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.domain.dao.Subscription;
import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.domain.dto.SubscriptionDto;
import ua.ivan909020.api.mappers.SubscriptionMapper;
import ua.ivan909020.api.mappers.SubscriptionMapperImpl;
import ua.ivan909020.api.services.PlanService;
import ua.ivan909020.api.services.SubscriptionService;
import ua.ivan909020.api.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ivan909020.api.JsonMapper.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(SubscriptionRestController.class)
public class SubscriptionRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private SubscriptionService subscriptionService;

    @MockBean
    private UserService userService;

    @MockBean
    private PlanService planService;

    @MockBean(classes = SubscriptionMapperImpl.class)
    private SubscriptionMapper subscriptionMapper;

    @Test
    @WithMockUser(value = "username")
    public void findById() throws Exception {
        Subscription subscription = createStubSubscription(1);
        SubscriptionDto subscriptionDto = convertToDto(subscription);

        when(subscriptionService.findById(1)).thenReturn(subscription);
        when(subscriptionMapper.toDto(subscription)).thenReturn(subscriptionDto);

        mock.perform(
                get("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(subscriptionDto)));

        verify(subscriptionService).findById(1);
        verify(subscriptionMapper).toDto(subscription);
    }

    @Test
    @WithMockUser(value = "username")
    public void findById_notFound() throws Exception {
        mock.perform(
                get("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(subscriptionService).findById(1);
        verify(subscriptionMapper, times(0)).toDto(any(Subscription.class));
    }

    @Test
    @WithMockUser(value = "username")
    public void create() throws Exception {
        Subscription subscription = createStubSubscription(null);
        SubscriptionDto subscriptionDto = convertToDto(subscription);
        Subscription createdSubscription = createStubSubscription(1);
        SubscriptionDto createdSubscriptionDto = convertToDto(createdSubscription);

        when(userService.existsById(1)).thenReturn(true);
        when(planService.existsById(1)).thenReturn(true);
        when(subscriptionMapper.toEntity(subscriptionDto)).thenReturn(subscription);
        when(subscriptionService.create(subscription)).thenReturn(createdSubscription);

        mock.perform(
                post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(subscriptionDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(toJson(createdSubscriptionDto)));

        verify(userService).existsById(1);
        verify(planService).existsById(1);
        verify(subscriptionMapper).toEntity(createdSubscriptionDto);
        verify(subscriptionService).create(subscription);
    }

    @Test
    @WithMockUser(value = "username")
    public void create_badRequest() throws Exception {
        Subscription subscription = createStubSubscription(null);
        subscription.setUser(null); // required parameter -> bad request
        SubscriptionDto subscriptionDto = convertToDto(subscription);

        mock.perform(
                post("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(subscriptionDto)))
                .andExpect(status().isBadRequest());

        verify(userService, times(0)).existsById(1);
        verify(planService, times(0)).existsById(1);
        verify(subscriptionMapper, times(0)).toEntity(subscriptionDto);
        verify(subscriptionService, times(0)).create(subscription);
    }

    @Test
    @WithMockUser(value = "username")
    public void update() throws Exception {
        Subscription subscription = createStubSubscription(1);
        SubscriptionDto subscriptionDto = convertToDto(subscription);

        when(subscriptionService.existsById(1)).thenReturn(true);
        when(userService.existsById(1)).thenReturn(true);
        when(planService.existsById(1)).thenReturn(true);
        when(subscriptionMapper.toEntity(subscriptionDto)).thenReturn(subscription);

        mock.perform(
                put("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(subscriptionDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(toJson(subscriptionDto)));

        verify(subscriptionService).existsById(1);
        verify(userService).existsById(1);
        verify(planService).existsById(1);
        verify(subscriptionMapper).toEntity(subscriptionDto);
        verify(subscriptionService).update(subscription);
    }

    @Test
    @WithMockUser(value = "username")
    public void update_notFound() throws Exception {
        Subscription subscription = createStubSubscription(1);
        SubscriptionDto subscriptionDto = convertToDto(subscription);

        when(subscriptionService.existsById(1)).thenReturn(false);

        mock.perform(
                put("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(subscriptionDto)))
                .andExpect(status().isNotFound());

        verify(subscriptionService).existsById(1);
        verify(userService, times(0)).existsById(1);
        verify(planService, times(0)).existsById(1);
        verify(subscriptionMapper, times(0)).toEntity(subscriptionDto);
        verify(subscriptionService, times(0)).update(subscription);
    }

    @Test
    @WithMockUser(value = "username")
    public void update_badRequest() throws Exception {
        Subscription subscription = createStubSubscription(null);
        subscription.setUser(null); // required parameter -> bad request
        SubscriptionDto subscriptionDto = convertToDto(subscription);

        when(planService.existsById(1)).thenReturn(true);
        when(subscriptionMapper.toEntity(subscriptionDto)).thenReturn(subscription);

        mock.perform(
                put("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(subscriptionDto)))
                .andExpect(status().isBadRequest());

        verify(subscriptionService, times(0)).existsById(1);
        verify(userService, times(0)).existsById(1);
        verify(planService, times(0)).existsById(1);
        verify(subscriptionMapper, times(0)).toEntity(subscriptionDto);
        verify(subscriptionService, times(0)).update(subscription);
    }

    @Test
    @WithMockUser(value = "username")
    public void deleteById() throws Exception {
        when(subscriptionService.existsById(1)).thenReturn(true);

        mock.perform(
                delete("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(subscriptionService).existsById(1);
        verify(subscriptionService).deleteById(1);
    }

    @Test
    @WithMockUser(value = "username")
    public void deleteById_notFound() throws Exception {
        when(subscriptionService.existsById(1)).thenReturn(false);

        mock.perform(
                delete("/subscriptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(subscriptionService).existsById(1);
        verify(subscriptionService, times(0)).deleteById(1);
    }

    @Test
    @WithMockUser("username")
    public void findAll() throws Exception {
        List<Subscription> subscriptions = Arrays.asList(createStubSubscription(1), createStubSubscription(2));
        List<SubscriptionDto> subscriptionsDto = convertToDto(subscriptions);

        when(subscriptionMapper.toDto(subscriptions)).thenReturn(subscriptionsDto);
        when(subscriptionService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(subscriptions));

        mock.perform(
                get("/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(subscriptionsDto)));

        verify(subscriptionMapper).toDto(subscriptions);
        verify(subscriptionService).findAll(any(Pageable.class));
    }

    private Subscription createStubSubscription(Integer id) {
        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setUser(createStubUser());
        subscription.setPlan(createStubPlan());
        subscription.setStartTime(LocalDateTime.now());
        subscription.setExpirationTime(LocalDateTime.now().plusDays(1));
        return subscription;
    }

    private User createStubUser() {
        User user = new User();
        user.setId(1);
        return user;
    }

    private Plan createStubPlan() {
        Plan plan = new Plan();
        plan.setId(1);
        return plan;
    }

    private SubscriptionDto convertToDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(subscription.getId());
        if (subscription.getUser() != null) {
            subscriptionDto.setUserId(subscription.getUser().getId());
            subscriptionDto.setPlanId(subscription.getUser().getId());
        }
        subscriptionDto.setStartTime(ISO_LOCAL_DATE_TIME.format(subscription.getStartTime()));
        subscriptionDto.setExpirationTime(ISO_LOCAL_DATE_TIME.format(subscription.getExpirationTime()));
        return subscriptionDto;
    }

    private List<SubscriptionDto> convertToDto(List<Subscription> subscriptions) {
        List<SubscriptionDto> result = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            result.add(convertToDto(subscription));
        }
        return result;
    }

}
