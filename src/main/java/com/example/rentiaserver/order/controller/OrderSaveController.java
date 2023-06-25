package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.LocationNotFoundException;
import com.example.rentiaserver.base.exception.UnsupportedArgumentException;
import com.example.rentiaserver.base.model.to.ResponseTo;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.service.OrderService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.service.UserService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = OrderSaveController.BASE_ENDPOINT)
public class OrderSaveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/orders";

    private final UserService userService;

    private final OrderService orderService;

    @Autowired
    public OrderSaveController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping(value = "/normal/add")
    @Transactional
    public ResponseTo saveOrder(@RequestBody OrderTo orderTo, HttpServletRequest request)
            throws EntityNotFoundException,
            UnsupportedArgumentException,
            ParseException,
            IOException,
            InterruptedException {

        Long userId = AuthenticationHelper.getUserId(request);
        UserPo user = userService.getUserPoById(userId);

        // TODO move to locationService
        markLocationTypes(orderTo);

        Long orderId = orderTo.getId();

        try {
            if (orderId != null) {
                OrderPo order = orderService.getOrderById(orderId);

                orderService.editOrder(order, orderTo);
            } else {
                orderService.addOrder(user, orderTo);
            }

            return ResponseTo.builder()
                    .operationSuccess(true)
                    .status(HttpStatus.OK)
                    .build();

        } catch (LocationNotFoundException ex) {
            return ResponseTo.builder()
                    .operationSuccess(false)
                    .status(HttpStatus.NOT_FOUND)
                    .message(ex.getMessage())
                    .build();
        }

    }

    private void markLocationTypes(OrderTo orderTo) {

        Optional.ofNullable(orderTo.getDestinationTo())
                .ifPresent(LocationTo::markLocationType);
        Optional.ofNullable(orderTo.getDestinationFrom())
                .ifPresent(LocationTo::markLocationType);

    }
}
