package hotel.web.model;

import hotel.web.entity.Hotel;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cvadmin
 */
public class HotelService {

        private IHotelDAO hotelDAO;

    public HotelService() {

        hotelDAO = new HotelDAO();
    }

    public final List<Hotel> getAllHotels() throws Exception {

        return hotelDAO.getAllHotels();
    }

    public final Hotel getHotelById(Long hotelId) throws Exception {

        return hotelDAO.getHotelById(hotelId);
    }

    public final int deleteHotel(Hotel hotel) throws Exception {

        return 0;
    }

    public final int addHotel(Hotel hotel) throws Exception {

        return 0;
    }

    public final int updateHotel(Hotel hotel) throws Exception {

        return 0;
    }

    public static void main(String[] args) throws Exception {

        HotelService hotelService = new HotelService();

        List<Hotel> hotels = hotelService.getAllHotels();
        for (Hotel hotel : hotels) {
            System.out.println(hotel);
        }
    }
}
