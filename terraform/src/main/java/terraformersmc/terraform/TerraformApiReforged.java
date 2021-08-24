package terraformersmc.terraform;


import com.terraformersmc.terraform.TerraformWood;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("terraform-api")
public class TerraformApiReforged {
	public TerraformApiReforged(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		new TerraformWood();
	}

	private void setup(FMLCommonSetupEvent event){
		for (TerraformMod mod : TerraformMod.MODULES){
			mod.setup(event);
		}
	}

	private void clientSetup(FMLClientSetupEvent event){
		for (TerraformMod mod : TerraformMod.MODULES){
			mod.clientSetup(event);
		}
	}
}
