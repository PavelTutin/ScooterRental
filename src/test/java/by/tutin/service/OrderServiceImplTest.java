package by.tutin.service;

import by.tutin.api.dao.OrderDao;
import by.tutin.model.Order;
import by.tutin.model.Scooter;
import by.tutin.model.Spot;
import by.tutin.model.dto.OrderDto;
import by.tutin.model.dto.OrderScooterInfoDto;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest extends TestCase {

    public void testAdd() {
        //Given
        OrderDto orderDto = new OrderDto();
        OrderDao orderDao =mock(OrderDao.class);


        //When


        //Then

    }

    public void testGetById() {

        //Given
        Scooter scooter = new Scooter();
        Spot spot = new Spot();
        Order order = new Order();
        //OrderDto orderDto = new OrderDto(1L,1L,2000, "ONCE",15,"2021-06-13T17:09:42.411", "2021-06-13T17:29:42.411",1L,1L);

        OrderScooterInfoDto orderScooterInfoDto = new OrderScooterInfoDto("Eleven",2000,"ONCE",15,"2021-06-13T17:09:42.411", "2021-06-13T17:29:42.411","Brest","Brest");
        OrderDao orderDao =mock(OrderDao.class);


        //When
       // when(orderDao.getById(1L)).thenReturn();

        //Then

    }

    public void testGetAll() {
    }

    public void testUpdate() {
    }

    public void testDelete() {
    }
}