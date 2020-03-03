package kr.ac.kopo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.kopo.dao.HotelRepo;
import kr.ac.kopo.dto.HotelSIO;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelRepo repo;

	private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

	@Override
	public void createDB() throws Exception {
		repo.createDB();
	}

	@Override
	public void dropDB() throws Exception {
		repo.dropDB();
		
	}

	@Override
	public void allsetDB() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(HotelSIO hotelSIO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HotelSIO selectOne(int studentid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelSIO> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelSIO> selectAllByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int studentid, HotelSIO hotelSIO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(HotelSIO hotelSIO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int studentid) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(HotelSIO hotelSIO) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

}
