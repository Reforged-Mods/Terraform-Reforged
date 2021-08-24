package terraformersmc.terraform;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

public abstract class TerraformMod {
	static final ObjectList<TerraformMod> MODULES = new ObjectArrayList<>();

	public TerraformMod(){
		MODULES.add(this);
	}

	public abstract void setup(FMLCommonSetupEvent event);

	public abstract void clientSetup(FMLClientSetupEvent event);
}
