package me.seba4316.antisleepmessage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import shop.ultracore.core.PluginHandler;
import shop.ultracore.core.packet.events.CPacketSentEvent;
import shop.ultracore.core.packet.packets.CClientboundSystemChatPacket;

public class AntiSleepMessage extends PluginHandler implements Listener {

	@Override
	public boolean pluginEnabled() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		return super.pluginEnabled();
	}

	@EventHandler
	public void onSystemChatPacketSent(CPacketSentEvent event) {
		if (!(event.getPacket() instanceof CClientboundSystemChatPacket)) return;
		CClientboundSystemChatPacket systemChatPacket = (CClientboundSystemChatPacket) event.getPacket();
		JsonObject object = new JsonParser().parse(systemChatPacket.getContent()).getAsJsonObject();
		JsonElement translateElement = object.get("translate");
		if (translateElement == null || !translateElement.isJsonPrimitive()) return;
		if (!"sleep.players_sleeping".equals(translateElement.getAsString())) return;
		/*
		JsonElement withElement = object.get("with");
		if (withElement == null || !withElement.isJsonArray()) return;
		JsonArray jsonArray = withElement.getAsJsonArray();
		int sleeping = jsonArray.get(0).getAsInt();
		int players = jsonArray.get(1).getAsInt();
		 */
		if (systemChatPacket.isFromServer())
			event.setCancelled(true);
	}

}