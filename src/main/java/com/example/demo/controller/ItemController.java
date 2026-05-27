package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Genre;
import com.example.demo.entity.Item;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class ItemController {

    private final Account account;

	private final ItemRepository itemRepository;
	private final GenreRepository genreRepository;
	private final UserRepository userRepository;

	public ItemController(ItemRepository itemRepository, GenreRepository genreRepository,
			UserRepository userRepository, Account account) {
		this.itemRepository = itemRepository;
		this.genreRepository = genreRepository;
		this.userRepository = userRepository;
		this.account = account;
	}

	//商品一覧
	@GetMapping("/items")
	public String index(
			@RequestParam(defaultValue = "") Integer genreId,
			@RequestParam(defaultValue = "") Integer targetPrice,
			Model model) {
		
		
		List<Genre> genreList = genreRepository.findAll();
		model.addAttribute("genres", genreList);

		//カテゴリー表示
		List<Item> itemList = null;
		if (genreId == null) {
			itemList = itemRepository.findByUserId(account.getId());
		} else {
			itemList = itemRepository.findByUserIdAndGenreId(account.getId(),genreId);
		}
		model.addAttribute("items", itemList);
		

		//収支計算
		int incomeAndOutcome = 0;
		int income = 0;
		int outcome = 0; 
		
		for (Item item : itemList) {
			if (item.getGenre().getIsIncome() == true) {
				incomeAndOutcome += item.getPrice();
				income += item.getPrice();
			} else {
				incomeAndOutcome -= item.getPrice();
				outcome -= item.getPrice();
			}
		}
		model.addAttribute("incomeAndOutcome", incomeAndOutcome);
		model.addAttribute("income", income);
		model.addAttribute("outcome", outcome);
		
		
		//目標金額設定
		User user = userRepository.findById(account.getId()).get();
		
		if(targetPrice != null) {
		user.setTargetPrice(targetPrice);
		userRepository.save(user);
		}
		
		model.addAttribute("targetPrice", user.getTargetPrice());
		
		return "items";
	}


	//項目追加画面の表示
	@GetMapping("/items/add")
	public String add(Model model) {
		List<Genre> genreList = genreRepository.findAll();
		model.addAttribute("genreList", genreList);
		return "addItem";
	}

	//項目追加機能
	@PostMapping("/items/add")
	public String store(
			@RequestParam (defaultValue = "")LocalDate addDate,
			@RequestParam (defaultValue = "")String itemName,
			@RequestParam (defaultValue = "")Integer genreId,
			@RequestParam (defaultValue = "")Integer price,
			@RequestParam (defaultValue = "")String comment,
			Model model) {
		
		List<String> errorList = new ArrayList<>();
		if(addDate == null) {
			errorList.add("日付は必須です");
		}
		if(genreId == null) {
			errorList.add("カテゴリーを選択してください");
		}
		if(price == null) {
			errorList.add("値段は必須です");
		}
		
		
		if(errorList.size() > 0) {
			List<Genre> genreList = genreRepository.findAll();
			model.addAttribute("genreList", genreList);
			model.addAttribute("errorList", errorList);
			
			model.addAttribute("addDate", addDate);
			model.addAttribute("itemName", itemName);  
			model.addAttribute("genreId", genreId);
			model.addAttribute("price", price);
			model.addAttribute("comment", comment);
			
			return "addItem";
		}

		Item item = new Item(
				userRepository.findById(account.getId()).get(),
				addDate,
				itemName,
				genreRepository.findById(account.getId()).get(),
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
			@RequestParam (defaultValue = "") LocalDate addDate,
			@RequestParam (defaultValue = "") String itemName,
			@RequestParam (defaultValue = "") Integer genreId,
			@RequestParam (defaultValue = "") Integer price,
			@RequestParam (defaultValue = "") String comment,
			Model model) {
		
		Item item = itemRepository.findById(id).get();
		item.setAddDate(addDate);
		item.setItemName(itemName);
		item.setGenre(genreRepository.findById(genreId).get());
		item.setPrice(price);
		item.setComment(comment);
		
		List<String> errorList = new ArrayList<>();
		if(price == null) {
			errorList.add("値段は必須です");
		}
		
		if(errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("item", item);
			model.addAttribute("genreList", genreRepository.findAll());
			model.addAttribute("price", price);
			return "editItem";
		}

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
