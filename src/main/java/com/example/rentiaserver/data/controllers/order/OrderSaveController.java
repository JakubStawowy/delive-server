package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import com.example.rentiaserver.security.to.ResponseTo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
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
    public ResponseTo saveOrder(@RequestBody OrderTo orderTo, HttpServletRequest request) {
        Optional<UserPo> optionalUserPo = userService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        if (optionalUserPo.isPresent()) {
            UserPo author = optionalUserPo.get();
            try {
                if (orderTo.getId() != null) {
                    Optional<OrderPo> optionalOrderPo = orderService.getOrderById(orderTo.getId());
                    if (optionalOrderPo.isPresent()) {
                        OrderPo orderPo = optionalOrderPo.get();
                        orderService.editOrder(orderPo, orderTo);
                    } else {
                        return new ResponseTo(false, "Order not found", HttpStatus.NOT_FOUND);
                    }
                } else {
                    orderService.addOrder(author, orderTo);
                }
                return new ResponseTo(true, null, HttpStatus.OK);
            } catch (InterruptedException | ParseException | IOException e) {
                return new ResponseTo(false, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (OrderService.LocationNotFoundException e) {
                return new ResponseTo(false, e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseTo(false, "User not found", HttpStatus.NOT_FOUND);
    }
}
