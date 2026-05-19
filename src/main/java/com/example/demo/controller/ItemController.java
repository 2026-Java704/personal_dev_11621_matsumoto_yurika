package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	private final ItemRepository itemRepository;
	
	public ItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	//商品一覧
	@GetMapping("items")
	public String index(Model model) {
		List<Item> itemList = itemRepository.findAll();
		model.addAttribute("items", itemList);
		
		return "items";
	}

}
