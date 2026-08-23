package qouteall.imm_ptl.peripheral;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Consumer;

public class PortalHelperItem extends BlockItem {
    private static boolean deprecationInformed = false;
    
    public PortalHelperItem(Block block, Properties settings) {
        super(block, settings);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            if (context.getPlayer() != null) {
                if (!deprecationInformed) {
                    deprecationInformed = true;
                    context.getPlayer().displayClientMessage(
                        Component.translatable(
                            "imm_ptl.portal_helper_deprecated",
                            Component.literal("/portal shape sculpt")
                                .withStyle(ChatFormatting.GOLD)
                        ), false
                    );
                }
            }
        }
        
        return super.useOn(context);
    }
    
    // just wrote something to make this compile that is kinda correct (or at least based on the new method signature, i dont think it ever gets called tho so i'll prob just delete
    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(
            ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        
        consumer.accept(Component.translatable("imm_ptl.portal_helper_tooltip"));
    }
}
