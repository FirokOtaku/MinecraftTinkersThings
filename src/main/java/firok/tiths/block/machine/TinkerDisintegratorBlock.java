package firok.tiths.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;
import slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder;
import slimeknights.tconstruct.library.tools.item.ToolItem;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.plugin.crt.managers.MaterialRecipeManager;
import slimeknights.tconstruct.shared.TinkerMaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * 匠魂粉碎机
 */
public class TinkerDisintegratorBlock extends Block
{
	public TinkerDisintegratorBlock()
	{
		super(
				Properties.create(Material.IRON)
						.harvestTool(ToolType.PICKAXE)
						.harvestLevel(HarvestLevels.WOOD)
						.hardnessAndResistance(4, 5)
		);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack stackHeld = player.getHeldItem(handIn);
		Item itemHeld = stackHeld.getItem();
		if(worldIn.isRemote || hit.getFace() != Direction.UP || stackHeld.isEmpty() || !(itemHeld instanceof ToolItem))
			return ActionResultType.FAIL;

		try
		{
			ToolStack stack = ToolStack.from(stackHeld);
			MaterialNBT materialNBT = stack.getMaterials();
			List<IMaterial> listMaterial = materialNBT.getMaterials();
			List<ItemStack> listPieces = new ArrayList<>(listMaterial.size() + 1);
			for(IMaterial mat : listMaterial)
			{
				/*
				todo 暂时没找到能用的api
				@see slimeknights.tconstruct.library.tools.part.MaterialItem.MaterialItem
				@see slimeknights.tconstruct.library.recipe.material.MaterialRecipeBuilder
				@see slimeknights.tconstruct.library.materials.MaterialRegistry
				*/
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ActionResultType.FAIL;
	}
}
