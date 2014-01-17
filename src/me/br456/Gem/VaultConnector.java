package me.br456.Gem;

import java.util.List;

import org.bukkit.ChatColor;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class VaultConnector implements Economy{
	
	SettingsManager settings = SettingsManager.getInstance();

	@Override
	public EconomyResponse bankBalance(String arg0) {
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		return null;
	}

	@Override
	public boolean createPlayerAccount(String name) {
		settings.setBalance(name, 0);
		return true;
	}

	@Override
	public boolean createPlayerAccount(String name, String world) {
		return createPlayerAccount(name);
	}

	@Override
	public String currencyNamePlural() {
		return "Gems";
	}

	@Override
	public String currencyNameSingular() {
		return "Gem";
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String name, double amnt) {
		settings.addBalance(name, amnt);
		return new EconomyResponse(amnt, settings.getBalance(name), ResponseType.SUCCESS, "");
	}

	@Override
	public EconomyResponse depositPlayer(String name, String world, double amnt) {
		return depositPlayer(name, amnt);
	}

	@Override
	public String format(double amnt) {
		return String.valueOf(amnt) + ChatColor.GREEN + " Gems";
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public double getBalance(String name) {
		return settings.getBalance(name);
	}

	@Override
	public double getBalance(String name, String world) {
		return getBalance(name);
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	@Override
	public String getName() {
		return "GemManager";
	}

	@Override
	public boolean has(String name, double amnt) {
		return settings.getBalance(name) >= amnt;
	}

	@Override
	public boolean has(String name, String world, double amnt) {
		return has(name, amnt);
	}

	@Override
	public boolean hasAccount(String arg0) {
		return false;
	}

	@Override
	public boolean hasAccount(String arg0, String arg1) {
		return false;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return null;
	}

	@Override
	public boolean isEnabled() {
		Gem.getPlugin().isEnabled();
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, double amnt) {
		return new EconomyResponse(amnt, settings.getBalance(name) - amnt, settings.removeBalance(name, amnt) ? ResponseType.SUCCESS : ResponseType.FAILURE, "Insufficient funds.");
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, String world, double amnt) {
		return withdrawPlayer(name, amnt);
	}

}
