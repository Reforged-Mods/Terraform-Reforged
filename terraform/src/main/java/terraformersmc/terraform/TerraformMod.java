package terraformersmc.terraform;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public abstract class TerraformMod {

	public TerraformMod(){

	}

	public abstract void setup(FMLCommonSetupEvent event);

	public abstract void clientSetup(FMLClientSetupEvent event);
}
