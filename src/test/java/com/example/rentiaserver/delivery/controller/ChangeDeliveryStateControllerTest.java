package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

class ChangeDeliveryStateControllerTest {

    private static final Long TEST_DELIVERY_ID = 1L;

    @Mock
    private DeliveryService deliveryService;

    private ChangeDeliveryStateController changeDeliveryStateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        changeDeliveryStateController = new ChangeDeliveryStateController(deliveryService);
    }

    @Test
    void pickPackage_ShouldCallPickPackageMethodInDeliveryService() throws EntityNotFoundException {

        // Given + When
        changeDeliveryStateController.pickPackage(TEST_DELIVERY_ID);

        // Then
        verify(deliveryService).pickPackage(TEST_DELIVERY_ID);
    }

    @Test
    void startDelivery_ShouldCallStartDeliveryMethodInDeliveryService() throws EntityNotFoundException {

        // Given + When
        changeDeliveryStateController.startDelivery(TEST_DELIVERY_ID);

        // Then
        verify(deliveryService).startDelivery(TEST_DELIVERY_ID);
    }

    @Test
    void finishDelivery_ShouldCallFinishDeliveryMethodInDeliveryService() throws EntityNotFoundException {

        // Given
        double clientLatitude = 37.12345;
        double clientLongitude = -122.98765;

        // When
        changeDeliveryStateController.finishDelivery(
                TEST_DELIVERY_ID,
                clientLatitude,
                clientLongitude);

        // Then
        verify(deliveryService).finishDelivery(anyLong(), any(LocationTo.class));
    }

    @Test
    void acceptDelivery_ShouldCallAcceptDeliveryMethodInDeliveryService() throws EntityNotFoundException {

        // Given + When
        changeDeliveryStateController.acceptDelivery(TEST_DELIVERY_ID);

        // Then
        verify(deliveryService).acceptDelivery(TEST_DELIVERY_ID);
    }

    @Test
    void discardDelivery_ShouldCallDiscardDeliveryMethodInDeliveryService() throws EntityNotFoundException {

        // Given + When
        changeDeliveryStateController.discardDelivery(TEST_DELIVERY_ID);

        // Then
        verify(deliveryService).discardDelivery(TEST_DELIVERY_ID);
    }

    @Test
    void closeDelivery_ShouldCallCloseDeliveryMethodInDeliveryService() throws EntityNotFoundException {

        // Given + When
        changeDeliveryStateController.closeDelivery(TEST_DELIVERY_ID);

        // Then
        verify(deliveryService).closeDelivery(TEST_DELIVERY_ID);
    }
}
