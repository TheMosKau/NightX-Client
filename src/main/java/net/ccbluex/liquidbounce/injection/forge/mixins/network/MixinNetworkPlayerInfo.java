package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.mojang.authlib.GameProfile;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Nick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(NetworkPlayerInfo.class)
public class MixinNetworkPlayerInfo {
    @Shadow
    @Final
    private GameProfile gameProfile;

    @Inject(method = "getLocationSkin", cancellable = true, at = @At("HEAD"))
    private void injectSkinProtect(CallbackInfoReturnable<ResourceLocation> cir) {
        Nick nameProtect = LiquidBounce.moduleManager.getModule(Nick.class);

        if (nameProtect.getState() && nameProtect.skinProtectValue.get()) {
            if (nameProtect.allPlayersValue.get() || Objects.equals(gameProfile.getId(), Minecraft.getMinecraft().getSession().getProfile().getId())) {
                cir.setReturnValue(DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
                cir.cancel();
            }
        }

    }
}