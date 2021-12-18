package com.example.rentiaserver.data.services.order;

import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.po.PackagePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public final class OrderService {

    private final OrderDao orderDao;
    private final FilteredOrderService filteredOrdersDao;
    private final PackageDao packageDao;

    @Autowired
    public OrderService(OrderDao orderDao, FilteredOrderService filteredOrdersDao, PackageDao packageRepository) {
        this.orderDao = orderDao;
        this.filteredOrdersDao = filteredOrdersDao;
        this.packageDao = packageRepository;
    }

    public void save(OrderPo orderPo) {
        orderDao.save(orderPo);
    }

    public void deleteById(Long id) {
        orderDao.deleteById(id);
    }

    public Optional<OrderPo> getOrderById(Long id) {
        return orderDao.findById(id);
    }

    public List<OrderPo> getAllOrders() {
        return orderDao.findAll();
    }

    public void saveAllPackages(Set<PackagePo> packagePos) {
        packageDao.saveAll(packagePos);
    }

    public void deleteAllByOrder(OrderPo orderPo) {
        packageDao.deleteAllByOrderPo(orderPo);
    }

    public List<OrderPo> findOrdersByAddresses(String initialAddress, String finalAddress, String minimalSalary,
                                               String requireTransportWithClient, boolean sortBySalary) {
        return filteredOrdersDao.findOrdersByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient, sortBySalary);
    }
}