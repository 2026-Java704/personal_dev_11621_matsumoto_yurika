package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class ItemController {

	private final ItemRepository itemRepository;
	private final GenreRepository genreRepository;
	private final UserRepository userRepository;
	
	public ItemController(ItemRepository itemRepository, GenreRepository genreRepository, UserRepository userRepository) {
		this.itemRepository = itemRepository;
		this.genreRepository = genreRepository;
		this.userRepository = userRepository;
	}
	
	//商品一覧
	@GetMapping("/items")
	public String index(Model model) {
		List<Item> itemList = itemRepository.findAll();
		model.addAttribute("items", itemList);
		
		return "items";
	}
	
	//項目追加画面の表示
	@GetMapping("/items/add")
	public String add() {
		return "addItem";
	}
	
	//項目追加機能
	@PostMapping("/items/add")
	public String store(
		@RequestParam LocalDate addDate,
		@RequestParam String itemName,
		@RequestParam Integer genreId,
		@RequestParam Integer price,
		@RequestParam String comment) {
		
		Item item = new Item(
				userRepository.findById(1).get(),
				addDate, 
				itemName, 
				genreRepository.findById(genreId).get(),
				price, 
				comment);
		itemRepository.save(item);
		return "redirect:/items";
	}
	
	//編集画面の表示
	@GetMapping("/items/{id}/edit")
	public String edit(@PathVariable Integer id, Model model) {
		Item item = itemRepository.findById(id).get();
		model.addAttribute("item", item);
		model.addAttribute("genreList", genreRepository.findAll());
		return "editItem";
	}
	
	
	//編集機能の追加
	@PostMapping("/items/{id}/edit")
	public String update(
			@PathVariable Integer id,
			@RequestParam LocalDate addDate,
			@RequestParam String itemName,
			@RequestParam Integer genreId,
			@RequestParam Integer price,
			@RequestParam String comment,
			Model model) {
		
		Item item = itemRepository.findById(id).get();
		item.setAddDate(addDate);
		item.setItemName(itemName);
		item.setGenre(genreRepository.findById(genreId).get());
		item.setPrice(price);
		item.setComment(comment);
		
		itemRepository.save(item);
		return "redirect:/items";
		
	}
	
	//削除処理
	@PostMapping("/items/{id}/delete")
	public String delete(@PathVariable Integer id) {
		
		itemRepository.deleteById(id);
		return "redirect:/items";
	}

}
