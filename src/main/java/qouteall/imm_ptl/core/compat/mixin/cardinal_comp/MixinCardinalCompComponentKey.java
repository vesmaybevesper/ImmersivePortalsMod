package qouteall.imm_ptl.core.compat.mixin.cardinal_comp;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import qouteall.imm_ptl.core.network.PacketRedirection;

@Mixin(ComponentKey.class)
public class MixinCardinalCompComponentKey {
    // redirect the entity sync packet
    @SuppressWarnings({"unchecked", "rawtypes"})
    @WrapOperation(
        method = "syncWith(Lnet/minecraft/server/level/ServerPlayer;Lorg/ladysnake/cca/api/v3/component/ComponentProvider;Lorg/ladysnake/cca/api/v3/component/sync/ComponentPacketWriter;Lorg/ladysnake/cca/api/v3/component/sync/PlayerSyncPredicate;)V",
        at = @At(
            value = "INVOKE",
            target = "Lorg/ladysnake/cca/api/v3/component/ComponentProvider;toComponentPacket(Lorg/ladysnake/cca/api/v3/component/ComponentKey;ZLnet/minecraft/network/RegistryFriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;"
        )
    )
    private @Nullable CustomPacketPayload redirectPacket(
            ComponentProvider instance, ComponentKey key, boolean required, RegistryFriendlyByteBuf data, Operation<CustomPacketPayload> original
    ) {
        Packet<?> packet = (Packet<?>) original.call(instance, key, required, data);
        
        if (instance instanceof Entity entity) {
            var redirected = PacketRedirection.createRedirectedMessage(
                entity.level().getServer(),
                entity.level().dimension(),
                (Packet<ClientGamePacketListener>) (Packet) packet
            );
            packet = redirected;
        }
        else if (instance instanceof BlockEntity blockEntity) {
            var redirected = PacketRedirection.createRedirectedMessage(
                blockEntity.getLevel().getServer(),
                blockEntity.getLevel().dimension(),
                (Packet<ClientGamePacketListener>) (Packet) packet
            );
            packet = redirected;
        }
        
        return ((ClientboundCustomPayloadPacket) packet).payload();
    }
}
