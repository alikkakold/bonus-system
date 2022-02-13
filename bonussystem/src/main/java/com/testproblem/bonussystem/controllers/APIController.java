package com.testproblem.bonussystem.controllers;

import com.testproblem.bonussystem.models.BalanceHistory;
import com.testproblem.bonussystem.models.Card;
import com.testproblem.bonussystem.models.Client;
import com.testproblem.bonussystem.repositories.BalanceHistoryRepository;
import com.testproblem.bonussystem.repositories.CardRepository;
import com.testproblem.bonussystem.repositories.ClientRepository;
import com.testproblem.bonussystem.responses.BasicResponse;
import com.testproblem.bonussystem.responses.CardBalanceResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	CardRepository cardRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	BalanceHistoryRepository balanceHistoryRepository;

	@PostMapping("/create/client") // создать нового клиента
	public Client createClient(@RequestBody Client client) {
		client.setIsActive(true);
		return clientRepository.save(client);
	}

	@PostMapping("/create/card") // создать новую карту
	public Card createCard(@RequestParam Integer balance, @RequestParam Integer number, @RequestParam Long clientId) throws Exception {
		Client client = clientRepository.findByIdAndIsActive(clientId, true)
				.orElseThrow(() -> new Exception("Client is not found or is not active"));
		Card card = new Card(balance, number, client);
		card.setClient(client);
		return cardRepository.save(card);
	}

	@PostMapping("/create/bonus") // создать новый бонус для карты
	public BalanceHistory createBonus(@RequestParam Long card_id, @RequestParam Integer value) throws Exception {
		Card card = cardRepository.findByIdAndIsActive(card_id, true)
				.orElseThrow(() -> new Exception("Card is not found"));
		card.setBalance(card.getBalance() + value);
		cardRepository.save(card);
		BalanceHistory balanceHistory = new BalanceHistory(value, new java.util.Date(), card);

		return balanceHistoryRepository.save(balanceHistory);
	}

	@GetMapping("/balance/total") // получить текущий баланс бонусов по заданной валюте
	public CardBalanceResponse getCardBalance(@RequestParam Integer currency, @RequestParam Long client_id) throws Exception {
		Card card = cardRepository.findByClientIdAndNumberAndIsActive(
				client_id,
				currency, true)
				.orElseThrow(() -> new Exception("Card was not found"));
		return new CardBalanceResponse(card.getNumber(), card.getBalance());
	}

	@GetMapping("/balance/details")
	public List<BalanceHistory> getBalanceHistory(@RequestParam String period, @RequestParam Long currency,
												  @RequestParam Long client_id) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm", Locale.ENGLISH);

		Date start = formatter.parse(period);
		Date end = DateUtils.addMonths(start, 1);
		return balanceHistoryRepository.findByIdAndCreated_atGreaterThanAndCreated_atLessThan(currency, start, end);
	}

	@PostMapping("/block/card") // блокировка карты по идентификатору
	public BasicResponse blockCard(@RequestParam Long card_id) throws Exception {
		Card card = cardRepository.findByIdAndIsActive(card_id, true)
				.orElseThrow(() -> new Exception("Card is not found or is not active"));

		card.setIsActive(false);
		cardRepository.save(card);
		return new BasicResponse(200, "Card was blocked");
	}

	@PostMapping("/block/client") // блокировка клиента по идентификатору
	public BasicResponse blockClient(@RequestParam Long card_id) throws Exception {
		Client client = clientRepository.findByIdAndIsActive(card_id, true)
				.orElseThrow(() -> new Exception("Client is not found or is not active"));
		client.setIsActive(false);
		clientRepository.save(client);
		return new BasicResponse(200, "Client was blocked");
	}

	@PostMapping("/delete/card") // удалить карту
	public BasicResponse deleteCard(@RequestParam Long card_id) throws Exception {
		Card card = cardRepository.findByIdAndIsActive(card_id, true)
				.orElseThrow(() -> new Exception("Card is not found or is not active"));
		cardRepository.delete(card);
		return new BasicResponse(200, "Card was deleted");
	}

	@PostMapping("/delete/client") // удалить клиента
	public BasicResponse deleteClient(@RequestParam Long card_id) throws Exception {
		Client client = clientRepository.findByIdAndIsActive(card_id, true)
				.orElseThrow(() -> new Exception("Client is not found or is not active"));
		clientRepository.delete(client);
		return new BasicResponse(200, "Client was deleted");
	}

	@PostMapping("/delete/bonus") // удалить начисление
	public BasicResponse deleteBonus(@RequestParam Long bonusId) throws Exception {
		BalanceHistory balanceHistory = balanceHistoryRepository.findById(bonusId)
				.orElseThrow(() -> new Exception("Balance History record was not found"));
		Card card = balanceHistory.getCard();
		card.setBalance(card.getBalance() - balanceHistory.getValue());
		cardRepository.save(card);
		balanceHistoryRepository.delete(balanceHistory);
		return new BasicResponse(200, "Balance History Record was deleted");
	}

}
