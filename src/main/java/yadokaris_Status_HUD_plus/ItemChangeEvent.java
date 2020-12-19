package yadokaris_Status_HUD_plus;

import java.util.Map;
import java.util.TimerTask;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ItemChangeEvent extends TimerTask {

	static long resetTime;
	private static int remainingHighlightTicks;
	static ItemStack highlightingItemStack;
	static int l;

	public void run() {

		l = Math.min((int)((float)this.remainingHighlightTicks * 256.0F / 40F), 255);

		if (Status_HUD.player != null) {
			ItemStack itemstack = Status_HUD.player.inventory.getCurrentItem();
			if (itemstack.isEmpty()) {
				this.remainingHighlightTicks = 0;
			}
			else if (!this.highlightingItemStack.isEmpty() && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && itemstack.isDamageable()) {
				if (this.remainingHighlightTicks > 0)  --this.remainingHighlightTicks;
			}
			else {
				this.remainingHighlightTicks = 200;
			}

			this.highlightingItemStack = itemstack;
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemstack);
			StringBuilder sb = new StringBuilder();

			for (Enchantment enchantment : enchantments.keySet()) {
				sb.append(enchantment.getDisplayName(enchantments.get(enchantment)).getString()).append("$n");
			}
			Status.Enchant.value = sb.toString();
		}
	}
}
