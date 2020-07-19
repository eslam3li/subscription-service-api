package ua.ivan909020.api.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import ua.ivan909020.api.domain.dao.Plan;
import ua.ivan909020.api.domain.dao.Subscription;
import ua.ivan909020.api.domain.dao.User;
import ua.ivan909020.api.exceptions.ValidationException;
import ua.ivan909020.api.services.impl.SubscriptionServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {

    @MockBean(classes = SubscriptionServiceImpl.class)
    private SubscriptionService subscriptionService;

    @Test
    public void existsById() {
        Integer subscriptionId = 1;

        when(subscriptionService.existsById(subscriptionId)).thenReturn(true);

        boolean isSubscriptionExists = subscriptionService.existsById(subscriptionId);

        assertTrue(isSubscriptionExists);
    }

    @Test
    public void existsById_idIsNull_throwsException() {
        Integer subscriptionId = null;

        when(subscriptionService.existsById(subscriptionId)).thenCallRealMethod();

        try {
            subscriptionService.existsById(subscriptionId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Subscription ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void findById() {
        Subscription subscription = createStubSubscription(1);

        when(subscriptionService.findById(subscription.getId())).thenReturn(subscription);

        Subscription foundSubscription = subscriptionService.findById(1);

        assertEquals(foundSubscription, subscription);
    }

    @Test
    public void findById_idIsNull_throwsException() {
        Integer subscriptionId = null;

        when(subscriptionService.findById(subscriptionId)).thenCallRealMethod();

        try {
            subscriptionService.findById(subscriptionId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Subscription ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void create() {
        Subscription subscriptionToCreate = createStubSubscription(null);

        when(subscriptionService.create(subscriptionToCreate)).thenAnswer(invocation -> {
            Subscription subscription = cloneStubSubscription((Subscription) invocation.getArguments()[0]);
            subscription.setId(1);
            return subscription;
        });

        Subscription createdSubscription = subscriptionService.create(subscriptionToCreate);

        Subscription expectedSubscription = createStubSubscription(createdSubscription.getId());
        expectedSubscription.setStartTime(createdSubscription.getStartTime());
        expectedSubscription.setExpirationTime(createdSubscription.getExpirationTime());

        assertNotNull(createdSubscription.getId());
        assertEquals(expectedSubscription, createdSubscription);
    }

    @Test
    public void create_null_throwsException() {
        Subscription subscriptionToCreate = null;

        when(subscriptionService.create(subscriptionToCreate)).thenCallRealMethod();

        try {
            subscriptionService.create(subscriptionToCreate);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Subscription must not be NULL", e.getMessage());
        }
    }

    @Test
    public void create_withId_throwsException() {
        Subscription subscriptionToCreate = createStubSubscription(1);

        when(subscriptionService.create(subscriptionToCreate)).thenCallRealMethod();

        try {
            subscriptionService.create(subscriptionToCreate);
            fail();
        } catch (ValidationException e) {
            assertEquals("Subscription ID must be NULL", e.getMessage());
        }
    }

    @Test
    public void update() {
        Subscription createdSubscription = createStubSubscription(1);

        when(subscriptionService.update(createdSubscription)).thenAnswer(invocation -> {
            Subscription subscription = cloneStubSubscription((Subscription) invocation.getArguments()[0]);
            subscription.setExpirationTime(LocalDateTime.parse("2020-08-01T10:00:00"));
            return subscription;
        });

        createdSubscription.setExpirationTime(LocalDateTime.parse("2020-08-01T10:00:00"));
        Subscription updatedSubscription = subscriptionService.update(createdSubscription);

        Subscription expectedSubscription = createStubSubscription(createdSubscription.getId());
        expectedSubscription.setStartTime(createdSubscription.getStartTime());
        expectedSubscription.setExpirationTime(createdSubscription.getExpirationTime());

        assertEquals(expectedSubscription, updatedSubscription);
    }

    @Test
    public void update_null_throwsException() {
        Subscription subscriptionToUpdate = null;

        when(subscriptionService.update(subscriptionToUpdate)).thenCallRealMethod();

        try {
            subscriptionService.update(subscriptionToUpdate);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Subscription must not be NULL", e.getMessage());
        }
    }

    @Test
    public void update_withoutId_throwsException() {
        Subscription subscriptionToUpdate = createStubSubscription(null);

        when(subscriptionService.update(subscriptionToUpdate)).thenCallRealMethod();

        try {
            subscriptionService.update(subscriptionToUpdate);
            fail();
        } catch (ValidationException e) {
            assertEquals("Subscription ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void deleteById() {
        Integer subscriptionId = 1;

        when(subscriptionService.findById(subscriptionId)).thenReturn(null);

        subscriptionService.deleteById(subscriptionId);
        Subscription receivedSubscription = subscriptionService.findById(subscriptionId);

        assertNull(receivedSubscription);
    }

    @Test
    public void deleteById_idIsNull_throwsException() {
        Integer subscriptionId = null;

        doCallRealMethod().when(subscriptionService).deleteById(subscriptionId);

        try {
            subscriptionService.deleteById(subscriptionId);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Subscription ID must not be NULL", e.getMessage());
        }
    }

    @Test
    public void findAll() {
        Page<Subscription> subscriptions = new PageImpl<>(Arrays.asList(createStubSubscription(1), createStubSubscription(2), createStubSubscription(3)));

        when(subscriptionService.findAll(any(Pageable.class))).thenReturn(subscriptions);

        Page<Subscription> receivedSubscriptions = subscriptionService.findAll(Pageable.unpaged());

        assertTrue(receivedSubscriptions.toList().containsAll(subscriptions.toList()));
    }

    @Test
    public void findAll_pageableIsNull_throwsException() {
        Pageable pageable = null;

        when(subscriptionService.findAll(pageable)).thenCallRealMethod();

        try {
            subscriptionService.findAll(pageable);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Pageable must not be NULL", e.getMessage());
        }
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

    private Subscription cloneStubSubscription(Subscription subscription) {
        Subscription clonedSubscription = new Subscription();
        clonedSubscription.setId(subscription.getId());
        clonedSubscription.setUser(subscription.getUser());
        clonedSubscription.setPlan(subscription.getPlan());
        clonedSubscription.setStartTime(subscription.getStartTime());
        clonedSubscription.setExpirationTime(subscription.getExpirationTime());
        return clonedSubscription;
    }

}
