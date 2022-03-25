package virtuoel.pehkui.mixin.reach.compat119plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.class_7265;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.StorageMinecartEntity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(StorageMinecartEntity.class)
public abstract class StorageMinecartEntityMixin implements class_7265
{
	@Inject(method = "canPlayerUse", at = @At("RETURN"), cancellable = true)
	private void canPlayerUseModifyDistance(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> info)
	{
		if (!info.getReturnValueZ())
		{
			final float scale = ScaleUtils.getEntityReachScale(playerEntity);
			
			if (scale > 1.0F && !this.isRemoved() && this.getPos().isInRange(playerEntity.getPos(), 8.0 * scale))
			{
				info.setReturnValue(true);
			}
		}
	}
}