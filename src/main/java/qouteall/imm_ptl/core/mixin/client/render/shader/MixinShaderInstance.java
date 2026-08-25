package qouteall.imm_ptl.core.mixin.client.render.shader;

import com.mojang.blaze3d.opengl.GlProgram;
import com.mojang.blaze3d.opengl.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.ducks.IEShader;

@Mixin(GlProgram.class)
public abstract class MixinShaderInstance implements IEShader {
    @Shadow
    @Nullable
    public abstract Uniform getUniform(String name);
    
    @Final
    @Shadow
    private int programId;
    
    @Unique
    private int ip_clippingEquationLocation;
    
    @Inject(
        method = "setupUniforms",
        at = @At("RETURN")
    )
    private void onLoadReferences(CallbackInfo ci) {
        RenderSystem.assertOnRenderThread();
        ip_clippingEquationLocation = GL20.glGetUniformLocation(programId, "iportal_ClippingEquation");
    }
    
    @Override
    public int ip_getClippingEquationUniformLocation() {
        return ip_clippingEquationLocation;
    }
}
