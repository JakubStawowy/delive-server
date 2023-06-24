package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.util.EntityNotFoundException;
import com.example.rentiaserver.data.util.LocationNotFoundException;
import com.example.rentiaserver.data.util.UnsupportedArgumentException;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import com.example.rentiaserver.security.to.ResponseTo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseTo saveOrder(@RequestBody OrderTo orderTo, HttpServletRequest request) throws EntityNotFoundException {
        Long userId = JsonWebTokenHelper.getRequesterId(request);
        UserPo user = userService.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserPo.class, userId));

        setAttributes(orderTo);

        Long orderId = orderTo.getId();

        try {
            if (orderId != null) {
                OrderPo order = orderService.getOrderById(orderId)
                        .orElseThrow(() -> new EntityNotFoundException(OrderPo.class, orderId));

                orderService.editOrder(order, orderTo);
            } else {
                orderService.addOrder(user, orderTo);
            }
        } catch (InterruptedException | ParseException | IOException ex) {
            return new ResponseTo(false, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (LocationNotFoundException | UnsupportedArgumentException ex) {
            return new ResponseTo(false, ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseTo(true, HttpStatus.OK);
    }

    private void setAttributes(OrderTo orderTo) {

        Optional.ofNullable(orderTo.getDestinationTo())
                .ifPresent(LocationTo::setLocationType);
        Optional.ofNullable(orderTo.getDestinationFrom())
                .ifPresent(LocationTo::setLocationType);

    }
}
