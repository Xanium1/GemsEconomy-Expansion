/*
 * Copyright Xanium Development (c) 2013-2018. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Xanium Development. Distribution, reproduction, taking snippets or claiming
 * any contents as your own will break the terms of the license, and void any agreements with you, the third party.
 * Thank you.
 */

package me.xanium.gemseconomy_expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xanium.gemseconomy.GemsEconomy;
import me.xanium.gemseconomy.economy.Account;
import me.xanium.gemseconomy.economy.AccountManager;
import me.xanium.gemseconomy.economy.Currency;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GemsEcoExpansion extends PlaceholderExpansion {

    @Override
    public boolean register() {
        if(!canRegister()){
            return false;
        }

        GemsEconomy gemsEconomy = (GemsEconomy) Bukkit.getPluginManager().getPlugin(this.getRequiredPlugin());

        if (gemsEconomy == null) {
            return false;
        }

        return super.register();
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(this.getRequiredPlugin()) != null;
    }

    @Override
    public String getIdentifier() {
        return "gemseconomy";
    }

    @Override
    public String getAuthor() {
        return "Xanium";
    }

    @Override
    public String getVersion() {
        return "1.5";
    }

    @Override
    public String getRequiredPlugin(){
        return "GemsEconomy";
    }

    @Override
    public String onRequest(OfflinePlayer player, String s) {
        if (player == null) {
            return "";
        }
        Account a = AccountManager.getAccount(player.getUniqueId());
        Currency dc = AccountManager.getDefaultCurrency();
        s = s.toLowerCase();

        if(s.equalsIgnoreCase("balance_default")){
            String amount = "";
            return amount + Math.round(a.getBalance(dc));
        }else if(s.equalsIgnoreCase("balance_default_formatted")){
            return dc.format(a.getBalance(dc));
        }

        else if(s.startsWith("balance_") || !s.startsWith("balance_default")) {
            String[] currencyArray = s.split("_");
            Currency c = AccountManager.getCurrency(currencyArray[1]);
            if (s.equalsIgnoreCase("balance_" + currencyArray[1] + "_formatted")) {
                return c.format(a.getBalance(c));
            } else {
                String amount = "";
                return amount + Math.round(a.getBalance(c));
            }
        }

        return null;
    }
}
