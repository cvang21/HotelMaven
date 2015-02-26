package hotel.maven.model;
import hotel.maven.entity.Hotel;
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

    public final long deleteHotel(Hotel hotel) throws Exception {

        return hotelDAO.deleteHotel(hotel);
    }

    public final long insertHotel(Hotel hotel) throws Exception {

        return hotelDAO.insertHotel(hotel);
    }

    public final long updateHotel(Hotel hotel) throws Exception {

        return hotelDAO.updateHotel(hotel);
    }

    public static void main(String[] args) throws Exception {

//        HotelService hotelService = new HotelService();
//
//        List<Hotel> hotels = hotelService.getAllHotels();
//        for (Hotel hotel : hotels) {
//            System.out.println(hotel);
//        }
    }
}