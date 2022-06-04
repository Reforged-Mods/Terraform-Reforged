package com.terraformersmc.terraform.boat.api;

import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.TerraformBoatTypeImpl;

import net.minecraft.item.Item;

/**
 * An interface representing a Terraform boat.
 */
public interface TerraformBoatType {
	/**
	 * {@return the {@linkplain net.minecraft.entity.vehicle.BoatEntity#getPickBlockStack() pick stack} and {@linkplain Item item} dropped when the {@linkplain TerraformBoatEntity boat entity} is broken}
	 */
	Item item();

	/**
	 * A builder for {@linkplain TerraformBoatType Terraform boat types}.
	 * 
	 * <p>To build a Terraform boat type:
	 * 
	 * <pre>{@code
	 *     TerraformBoatType boat = new TerraformBoatType.Builder()
	 *         .item(ExampleModItems.MAHOGANY_BOAT)
	 *         .build();
	 * }</pre>
	 */
	class Builder {
		private Item item;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.item);
		}

		/**
		 * @see TerraformBoatType#item
		 */
		public Builder item(Item item) {
			this.item = item;
			return this;
		}
	}
}
