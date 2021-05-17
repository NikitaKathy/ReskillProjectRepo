package com.oms.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.user.dto.BuyerDTO;
import com.oms.user.dto.SellerDTO;
import com.oms.user.entity.Buyer;
import com.oms.user.entity.Seller;
import com.oms.user.exception.UserMsException;
import com.oms.user.repository.BuyerRepository;
import com.oms.user.repository.SellerRepository;
import com.oms.user.validator.UserValidator;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	
	private static int b;
	private static int s;
	
	static {
		b=100;
		s=100;
	}
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private SellerRepository sellerRepository;

	@Override
	public String buyerRegistration(BuyerDTO buyerDTO) throws UserMsException {
		// TODO Auto-generated method stub
		
		UserValidator.validateBuyer(buyerDTO);
		
		Buyer buy = buyerRepository.findByPhoneNumber(buyerDTO.getPhoneNumber());
		
		if(buy != null)
			throw new UserMsException("Buyer already present");
		
		String id = "B" + b++;
		
		buy = new Buyer();
		
		buy.setBuyerId(id);
		buy.setEmail(buyerDTO.getEmail());
		buy.setName(buyerDTO.getName());
		buy.setPhoneNumber(buyerDTO.getPhoneNumber());
		buy.setPassword(buyerDTO.getPassword());
		buy.setIsActive("False");
		buy.setIsPrivileged("False");
		buy.setRewardPoints("0");
		
		buyerRepository.save(buy);
		
		return buy.getBuyerId();
	}

	@Override
	public String sellerRegistration(SellerDTO sellerDTO) throws UserMsException {
		// TODO Auto-generated method stub
		
		UserValidator.validateSeller(sellerDTO);
		
		Seller seller = sellerRepository.findByPhoneNumber(sellerDTO.getPhoneNumber());
		
		if(seller != null)
			throw new UserMsException("Seller Already present");
		
		String id = "S" + s++;
		
		seller = new Seller();
		
		seller.setEmail(sellerDTO.getEmail());
		seller.setSellerId(id);
		seller.setName(sellerDTO.getName());
		seller.setPassword(sellerDTO.getPassword());
		seller.setIsActive("False");
		seller.setPhoneNumber(sellerDTO.getPhoneNumber());
		
		sellerRepository.save(seller);
		
		return seller.getSellerId();
	}

	@Override
	public String buyerLogin(String email, String password) throws UserMsException {
		// TODO Auto-generated method stub
	
		if(!UserValidator.validateEmail(email))
			throw new UserMsException("Enter valid email id");
		
		Buyer buyer = buyerRepository.findByEmail(email);
		
//		System.out.println(buyer);
		
		if(buyer == null)
			throw new UserMsException("Wrong credentials");
		
		if(!buyer.getPassword().equals(password))
			throw new UserMsException("Wrong credentials");
		
		buyer.setIsActive("True");
			
		buyerRepository.save(buyer);
		
		return "Login Success";
	}

	@Override
	public String sellerLogin(String email, String password) throws UserMsException {

		if(!UserValidator.validateEmail(email))
			throw new UserMsException("Enter valid email id");
		
		Seller seller = sellerRepository.findByEmail(email);
		
//		System.out.println(seller);
		
		if(seller == null)
			throw new UserMsException("Wrong credentials");
		
		if(!seller.getPassword().equals(password))
			throw new UserMsException("Wrong credentials");
		
		seller.setIsActive("True");
			
		sellerRepository.save(seller);
		
		return "Login Success";
	}

	@Override
	public String deleteBuyer(String id){
		
		Buyer buyer = buyerRepository.findByBuyerId(id);
		
		buyerRepository.delete(buyer);
		
		return "Account successfully deleted";
	}

	@Override
	public String deleteSeller(String id) {
		
		Seller seller = sellerRepository.findBySellerId(id);
		
		sellerRepository.delete(seller);
		
		return "Account successfully deleted";
	}

}