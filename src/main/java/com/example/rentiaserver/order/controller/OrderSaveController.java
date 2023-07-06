package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.LocationNotFoundException;
import com.example.rentiaserver.base.exception.UnsupportedArgumentException;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.service.OrderService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = OrderSaveController.BASE_ENDPOINT)
public class OrderSaveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/orders";

    private final OrderService orderService;

    @Autowired
    public OrderSaveController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/normal/add")
    @Transactional
    public void saveOrder(@RequestBody OrderTo order, HttpServletRequest request)
            throws EntityNotFoundException,
            UnsupportedArgumentException,
            ParseException,
            IOException,
            InterruptedException,
            LocationNotFoundException {

        // TODO to remove
        if (order.getAuthorId() == null) {
            Long userId = AuthenticationHelper.getUserId(request);
            order.setAuthorId(userId);
        }

        orderService.saveOrder(order);
    }
}
