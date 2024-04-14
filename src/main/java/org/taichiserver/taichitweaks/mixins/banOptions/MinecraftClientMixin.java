package org.taichiserver.taichitweaks.mixins.banOptions;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.taichiserver.taichitweaks.features.BanOptions;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;
    @Unique
    private int count = 0;

    @Shadow @Nullable public ClientWorld world;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if(world == null) return;

        count += 1;
        if( count % 20 != 0 ) return;
        count = 0;

        String reason = BanOptions.checker();

        if(reason != null) {
            world.disconnect();
            MinecraftClient mc = (MinecraftClient) (Object) this;
            mc.disconnect(new DisconnectedScreen(new TitleScreen(), Text.of("禁止機能が有効になっています"), Text.of(reason)));
        }
    }
}